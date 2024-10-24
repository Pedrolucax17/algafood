package com.algafood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.algafood.domain.model.Cozinha;
import com.algafood.domain.repository.CozinhaRepository;
import com.algafood.exceptions.EntidadeEmUsoException;
import com.algafood.exceptions.EntidadeNaoEncontradaException;

@Service
public class CozinhaService {
	
	@Autowired
	private CozinhaRepository repository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha salvar(Cozinha cozinha){
		return repository.salvar(cozinha);
	}
	
	@DeleteMapping
	public void remover(Long id) {
		try {
			
			repository.remover(id);
			
		}
		catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de cozinha com código %d", id)
			);
		}
		catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d não pode ser removida, pois está em uso", id)
			);
		}
	}
}
