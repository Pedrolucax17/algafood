package com.algafood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.EstadoRepository;
import com.algafood.exceptions.EntidadeEmUsoException;
import com.algafood.exceptions.EntidadeNaoEncontradaException;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repository;
	
	public Estado salvar(Estado estado) {
		return repository.salvar(estado);
	}
	
	
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
