package com.wesleylima.estudo.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wesleylima.estudo.domain.Categoria;
import com.wesleylima.estudo.dto.CategoriaDTO;
import com.wesleylima.estudo.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

	@Autowired private CategoriaService categoriaService;
	
	@RequestMapping(value = "/{id}",method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable final Integer id) {
		Categoria categoria = categoriaService.findById(id);

		return ResponseEntity.ok().body(categoria);
	} 
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> save(@RequestBody Categoria categoria) {
		categoria = categoriaService.save(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(categoria.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	} 
	
	@RequestMapping(value = "/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(
			@RequestBody Categoria categoria, 
			@PathVariable final Integer id) {
		categoria.setId(id);
		categoria = categoriaService.save(categoria);

		return ResponseEntity.noContent().build();
	} 
	
	@RequestMapping(value = "/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable final Integer id) {
		categoriaService.deleteById(id);

		return ResponseEntity.noContent().build();
	} 
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> categoria = categoriaService.findAll();
		List<CategoriaDTO> listDto = categoria.stream().map(cat -> new CategoriaDTO(cat)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	} 
}
