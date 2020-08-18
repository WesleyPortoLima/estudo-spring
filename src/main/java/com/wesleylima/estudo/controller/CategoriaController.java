package com.wesleylima.estudo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wesleylima.estudo.domain.Categoria;
import com.wesleylima.estudo.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

	@Autowired private CategoriaService categoriaService;
	
	@RequestMapping(value = "/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable final Integer id) {
		Categoria categoria = categoriaService.findById(id);

		return ResponseEntity.ok().body(categoria);
	} 
}
