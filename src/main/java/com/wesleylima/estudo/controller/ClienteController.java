package com.wesleylima.estudo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wesleylima.estudo.domain.Cliente;
import com.wesleylima.estudo.service.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired private ClienteService ClienteService;
	
	@RequestMapping(value = "/{id}",method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable final Integer id) {
		Cliente Cliente = ClienteService.findById(id);

		return ResponseEntity.ok().body(Cliente);
	} 
}
