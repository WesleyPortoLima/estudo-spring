package com.wesleylima.estudo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.wesleylima.estudo.domain.Categoria;
import com.wesleylima.estudo.service.exception.DataIntegrityException;
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
	
	public Categoria save(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}
	
	public Categoria update(final Categoria categoria) {
		findById(categoria.getId());
		return categoriaRepository.save(categoria);
	}
	
	public void deleteById(final Integer id) {
		findById(id);
		try {
		categoriaRepository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar uma categoria que possua produtos!");
		}
	}
}
