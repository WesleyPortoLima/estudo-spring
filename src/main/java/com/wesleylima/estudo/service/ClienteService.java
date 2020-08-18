package com.wesleylima.estudo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesleylima.estudo.domain.Cliente;
import com.wesleylima.estudo.repository.ClienteRepository;
import com.wesleylima.estudo.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired private ClienteRepository clienteRepository;
	
	public Cliente findById(final Integer id) {
		return clienteRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
						+ ", Tipo: " + Cliente.class.getName()));
	}
}
