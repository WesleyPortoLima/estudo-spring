package com.wesleylima.estudo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesleylima.estudo.domain.Categoria;
import com.wesleylima.estudo.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired private CategoriaRepository categoriaRepository;
	
	public Categoria findById(final Integer id) {
		return categoriaRepository.findById(id).orElse(null);
	}
}
