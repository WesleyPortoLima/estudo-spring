package com.wesleylima.estudo.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wesleylima.estudo.domain.Pedido;
import com.wesleylima.estudo.service.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired private PedidoService pedidoService;
	
	@RequestMapping(value = "/{id}",method=RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable final Integer id) {
		Pedido pedido = pedidoService.findById(id);

		return ResponseEntity.ok().body(pedido);
	} 
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> save(@Valid @RequestBody Pedido pedido) {
		pedido = pedidoService.save(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(pedido.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	} 
}
