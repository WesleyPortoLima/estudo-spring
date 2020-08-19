package com.wesleylima.estudo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.wesleylima.estudo.domain.Cliente;
import com.wesleylima.estudo.dto.ClienteDTO;
import com.wesleylima.estudo.repository.ClienteRepository;
import com.wesleylima.estudo.service.exception.DataIntegrityException;
import com.wesleylima.estudo.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired private ClienteRepository clienteRepository;
	
	public Cliente findById(final Integer id) {
		return clienteRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
						+ ", Tipo: " + Cliente.class.getName()));
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
			throw new DataIntegrityException("Não é possível deletar porquê há entidades relacionadas!");
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
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null);
	}
	
	private void updateData(Cliente obj, Cliente newObj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
