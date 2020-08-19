package com.wesleylima.estudo.dto;

import java.io.Serializable;

import com.wesleylima.estudo.domain.Categoria;

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 2400851441083923333L;

	private Integer id;
	
	private String nome;
	
	public CategoriaDTO() {}
	
	public CategoriaDTO(Categoria categoria) {
		id = categoria.getId();
		nome = categoria.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
