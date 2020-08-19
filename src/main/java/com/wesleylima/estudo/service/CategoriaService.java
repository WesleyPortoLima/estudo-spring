package com.wesleylima.estudo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.wesleylima.estudo.domain.Categoria;
import com.wesleylima.estudo.repository.CategoriaRepository;
import com.wesleylima.estudo.service.exception.DataIntegrityException;
import com.wesleylima.estudo.service.exception.ObjectNotFoundException;

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
	
	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}
	
	public Page<Categoria> findPage(final Integer page, final Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return categoriaRepository.findAll(pageRequest);
	}
}
