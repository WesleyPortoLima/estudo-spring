package com.wesleylima.estudo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wesleylima.estudo.domain.Cidade;
import com.wesleylima.estudo.domain.Cliente;
import com.wesleylima.estudo.domain.Endereco;
import com.wesleylima.estudo.domain.enums.Perfil;
import com.wesleylima.estudo.domain.enums.TipoCliente;
import com.wesleylima.estudo.dto.ClienteDTO;
import com.wesleylima.estudo.dto.ClienteNewDTO;
import com.wesleylima.estudo.repository.ClienteRepository;
import com.wesleylima.estudo.repository.EnderecoRepository;
import com.wesleylima.estudo.security.UserSS;
import com.wesleylima.estudo.service.exception.AuthorizationException;
import com.wesleylima.estudo.service.exception.DataIntegrityException;
import com.wesleylima.estudo.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired private ClienteRepository clienteRepository;
	
	@Autowired private EnderecoRepository enderecoRepository;
	
	@Autowired private BCryptPasswordEncoder pe;
	
	public Cliente findById(final Integer id) {
		UserSS user = UserService.authenticated();
		
		if (user == null || !user.hasRole(Perfil.ADMIN)) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		return clienteRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
						+ ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente save(Cliente cliente) {
		cliente.setId(null);
		cliente = clienteRepository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		
		return cliente;
	}
	
	public Cliente update(Cliente cliente) {
		Cliente newCli = findById(cliente.getId());
		updateData(cliente, newCli);
		
		return clienteRepository.save(cliente);
	}
	
	public void deleteById(final Integer id) {
		findById(id);
		try {
		clienteRepository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar porquê há pedidos relacionados!");
		}
	}
	
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> findPage(final Integer page, final Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO dto) {
		Cliente cli = new Cliente(
				null, 
				dto.getNome(), 
				dto.getEmail(), 
				dto.getCpfOuCnpj(), 
				TipoCliente.toEnum(dto.getTipo()), 
				pe.encode(dto.getSenha()));
		
		Cidade cid = new Cidade(dto.getCidadeId(), null, null);
		
		Endereco end = new Endereco(
				null, 
				dto.getLogradouro(), 
				dto.getNumero(),
				dto.getComplemento(), 
				dto.getBairro(), 
				dto.getCep(), 
				cli, 
				cid);
		
		cli.getEnderecos().add(end);
		
		cli.getTelefones().add(dto.getTelefone1());
		
		if (dto.getTelefone2() != null) {
			cli.getTelefones().add(dto.getTelefone2());
		}
		
		if (dto.getTelefone3() != null) {
			cli.getTelefones().add(dto.getTelefone3());
		}
		
		return cli;
	}
	
	private void updateData(Cliente obj, Cliente newObj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
