package com.wesleylima.estudo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wesleylima.estudo.domain.Cliente;
import com.wesleylima.estudo.domain.ItemPedido;
import com.wesleylima.estudo.domain.PagamentoComBoleto;
import com.wesleylima.estudo.domain.Pedido;
import com.wesleylima.estudo.domain.enums.EstadoPagamento;
import com.wesleylima.estudo.repository.ItemPedidoRepository;
import com.wesleylima.estudo.repository.PagamentoRepository;
import com.wesleylima.estudo.repository.PedidoRepository;
import com.wesleylima.estudo.security.UserSS;
import com.wesleylima.estudo.service.exception.AuthorizationException;
import com.wesleylima.estudo.service.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired private PedidoRepository pedidoRepository;
	
	@Autowired private BoletoService boletoService;
	
	@Autowired private PagamentoRepository pagamentoRepository;
	
	@Autowired private ProdutoService produtoService;
	
	@Autowired private ClienteService clienteService;
	
	@Autowired private EmailService emailService;
	
	@Autowired private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido findById(final Integer id) {
		return pedidoRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
						+ ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido save(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteService.findById(pedido.getCliente().getId()));
		pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
		}
		pedido = pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for(ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.findById(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}
		
		itemPedidoRepository.saveAll(pedido.getItens());
		
		emailService.sendOrderConfirmationHtmlEmail(pedido);
		
		return pedido;
	}
	
	public Page<Pedido> findPage(final Integer page, final Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		
		if (user == null) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Cliente cliente = clienteService.findById(user.getId());
		
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}
}
