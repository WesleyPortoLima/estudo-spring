package com.wesleylima.estudo;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wesleylima.estudo.domain.Categoria;
import com.wesleylima.estudo.domain.Cidade;
import com.wesleylima.estudo.domain.Cliente;
import com.wesleylima.estudo.domain.Endereco;
import com.wesleylima.estudo.domain.Estado;
import com.wesleylima.estudo.domain.Pagamento;
import com.wesleylima.estudo.domain.PagamentoComBoleto;
import com.wesleylima.estudo.domain.PagamentoComCartao;
import com.wesleylima.estudo.domain.Pedido;
import com.wesleylima.estudo.domain.Produto;
import com.wesleylima.estudo.domain.enums.EstadoPagamento;
import com.wesleylima.estudo.domain.enums.TipoCliente;
import com.wesleylima.estudo.repository.CategoriaRepository;
import com.wesleylima.estudo.repository.CidadeRepository;
import com.wesleylima.estudo.repository.ClienteRepository;
import com.wesleylima.estudo.repository.EnderecoRepository;
import com.wesleylima.estudo.repository.EstadoRepository;
import com.wesleylima.estudo.repository.PagamentoRepository;
import com.wesleylima.estudo.repository.PedidoRepository;
import com.wesleylima.estudo.repository.ProdutoRepository;

@SpringBootApplication
public class EstudoApplication implements CommandLineRunner {

	@Autowired private CategoriaRepository categoriaRepository;
	
	@Autowired private ProdutoRepository produtoRepository;
	
	@Autowired private CidadeRepository cidadeRepository;
	
	@Autowired private EstadoRepository estadoRepository;
	
	@Autowired private ClienteRepository clienteRepository;
	
	@Autowired private EnderecoRepository enderecoRepository;
	
	@Autowired private PedidoRepository pedidoRepository;
	
	@Autowired private PagamentoRepository pagamentoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(EstudoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		
		Produto p1 = new Produto(null, "Computador", 2000.0);
		Produto p2 = new Produto(null, "Impressora", 800.0);
		Produto p3 = new Produto(null, "Mouse", 80.0);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "São Paulo");
		Estado est2 = new Estado(null, "Minas Gerais");
		
		Cidade c1 = new Cidade(null, "Campinas", est1);
		Cidade c2 = new Cidade(null, "Uberlândia", est2);
		Cidade c3 = new Cidade(null, "São Paulo", est1);
		
		est1.getCidades().add(c2);
		est2.getCidades().addAll(Arrays.asList(c1,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12345678910", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefone().addAll(Arrays.asList("12345678","987654321"));
		
		Endereco e1 = new Endereco(null, "Rua flores", "300", "APTO 03", "Jardim", "12345020", cli1, c2);
		Endereco e2 = new Endereco(null, "Rua 3", "200", "APTO 05", "Paraíso", "12345020", cli1, c3);
		
		cli1.getEndereco().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2019 10:32"), cli1, e1);
		
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2019 10:32"), cli1, e2);
		
		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);
		
		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2019 00:00"), null);
		ped2.setPagamento(pgto2);
		
		cli1.getPedido().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgto1, pgto2));
		
	}

}
