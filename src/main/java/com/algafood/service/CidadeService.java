package com.algafood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.domain.model.Cidade;
import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.CidadeRepository;
import com.algafood.domain.repository.EstadoRepository;
import com.algafood.exceptions.EntidadeEmUsoException;
import com.algafood.exceptions.EntidadeNaoEncontradaException;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.buscar(estadoId);
		
		if(estado == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("A entidde com o código %d não existe", estadoId)
			);
		}
		
		cidade.setEstado(estado);
		
		return repository.salvar(cidade);
		
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
