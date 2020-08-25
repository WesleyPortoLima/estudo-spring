package com.wesleylima.estudo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesleylima.estudo.domain.Cidade;
import com.wesleylima.estudo.repository.CidadeRepository;

@Service
public class CidadeService {

	@Autowired private CidadeRepository cidadeRepository;
	
	public List<Cidade> findAll(final Integer estadoId) {
		return cidadeRepository.findAllByEstadoIdOrderByNome(estadoId);
	}
}
