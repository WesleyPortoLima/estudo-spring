package com.wesleylima.estudo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesleylima.estudo.domain.Pedido;
import com.wesleylima.estudo.service.exception.ObjectNotFoundException;
import com.wesleylima.estudo.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired private PedidoRepository pedidoRepository;
	
	public Pedido findById(final Integer id) {
		return pedidoRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
						+ ", Tipo: " + Pedido.class.getName()));
	}
}
