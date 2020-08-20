package com.wesleylima.estudo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.wesleylima.estudo.domain.Categoria;
import com.wesleylima.estudo.domain.Produto;
import com.wesleylima.estudo.repository.CategoriaRepository;
import com.wesleylima.estudo.repository.ProdutoRepository;
import com.wesleylima.estudo.service.exception.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired private ProdutoRepository produtoRepository;
	
	@Autowired private CategoriaRepository categoriaRepository;
	
	public Produto findById(final Integer id) {
		return produtoRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
						+ ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> search(
			final String nome, 
			final List<Integer> ids, 
			final Integer page, 
			final Integer linesPerPage, 
			final String orderBy, 
			final String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		
		return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
