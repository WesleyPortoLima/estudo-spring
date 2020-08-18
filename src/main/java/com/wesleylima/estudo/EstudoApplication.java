package com.wesleylima.estudo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wesleylima.estudo.domain.Categoria;
import com.wesleylima.estudo.domain.Cidade;
import com.wesleylima.estudo.domain.Estado;
import com.wesleylima.estudo.domain.Produto;
import com.wesleylima.estudo.repository.CategoriaRepository;
import com.wesleylima.estudo.repository.CidadeRepository;
import com.wesleylima.estudo.repository.EstadoRepository;
import com.wesleylima.estudo.repository.ProdutoRepository;

@SpringBootApplication
public class EstudoApplication implements CommandLineRunner {

	@Autowired private CategoriaRepository categoriaRepository;
	
	@Autowired private ProdutoRepository produtoRepository;
	
	@Autowired private CidadeRepository cidadeRepository;
	
	@Autowired private EstadoRepository estadoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(EstudoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		
		Produto p1 = new Produto(null, "Computador", 2000.0);
		Produto p2 = new Produto(null, "Impressora", 800.0);
		Produto p3 = new Produto(null, "Mouse", 80.0);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "São Paulo");
		Estado est2 = new Estado(null, "Minas Gerais");
		
		Cidade c1 = new Cidade(null, "Campinas", est1);
		Cidade c2 = new Cidade(null, "Uberlândia", est2);
		Cidade c3 = new Cidade(null, "São Paulo", est1);
		
		est1.getCidades().add(c2);
		est2.getCidades().addAll(Arrays.asList(c1,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
	}

}
