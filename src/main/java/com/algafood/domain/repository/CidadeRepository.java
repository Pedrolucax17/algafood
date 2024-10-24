package com.algafood.domain.repository;

import java.util.List;

import com.algafood.domain.model.Cidade;

public interface CidadeRepository {
	
	List<Cidade> listar();
	Cidade buscar(Long id);
	Cidade salvar(Cidade restaurante);
	void remover(Cidade restaurante);

}
