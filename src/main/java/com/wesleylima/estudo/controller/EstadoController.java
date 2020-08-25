package com.wesleylima.estudo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wesleylima.estudo.domain.Cidade;
import com.wesleylima.estudo.domain.Estado;
import com.wesleylima.estudo.dto.CidadeDTO;
import com.wesleylima.estudo.dto.EstadoDTO;
import com.wesleylima.estudo.service.CidadeService;
import com.wesleylima.estudo.service.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

	@Autowired private EstadoService estadoService;
	
	@Autowired private CidadeService cidadeService;

	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> estados = estadoService.findAll();
		List<EstadoDTO> listDto = estados.stream().map(estado -> new EstadoDTO(estado)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
	} 
	
	@RequestMapping(value ="/{estadoId}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findAllCidades(@PathVariable final Integer estadoId) {
		List<Cidade> cidades = cidadeService.findAll(estadoId);
		List<CidadeDTO> listDto = cidades.stream().map(cidade -> new CidadeDTO(cidade)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
	} 

	
}
