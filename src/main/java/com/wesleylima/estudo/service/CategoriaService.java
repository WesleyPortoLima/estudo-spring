package com.wesleylima.estudo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesleylima.estudo.domain.Categoria;
import com.wesleylima.estudo.service.exception.ObjectNotFoundException;
import com.wesleylima.estudo.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired private CategoriaRepository categoriaRepository;
	
	public Categoria findById(final Integer id) {
		return categoriaRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
						+ ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria save(final Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}
}
