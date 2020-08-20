package com.wesleylima.estudo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wesleylima.estudo.controller.utils.URL;
import com.wesleylima.estudo.domain.Produto;
import com.wesleylima.estudo.dto.ProdutoDTO;
import com.wesleylima.estudo.service.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

	@Autowired private ProdutoService produtoService;
	
	@RequestMapping(value = "/{id}",method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable final Integer id) {
		Produto produto = produtoService.findById(id);

		return ResponseEntity.ok().body(produto);
	} 
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "ASC") String nome,
			@RequestParam(value = "categorias", defaultValue = "ASC") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "sortBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> categoriasList = URL.decodeIntList(categorias);
		
		Page<Produto> produtos = produtoService.search(nomeDecoded, categoriasList, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = produtos.map(prod -> new ProdutoDTO(prod));
		return ResponseEntity.ok().body(listDto);
	} 
}
