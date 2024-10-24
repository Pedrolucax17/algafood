package com.algafood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.domain.model.Cozinha;
import com.algafood.domain.model.Restaurante;
import com.algafood.domain.repository.CozinhaRepository;
import com.algafood.domain.repository.RestauranteRepository;
import com.algafood.exceptions.EntidadeNaoEncontradaException;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository repository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		
		if(cozinha == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("A entidde com o código %d não existe", cozinhaId)
			);
		}
		
		restaurante.setCozinha(cozinha);
		
		return repository.salvar(restaurante);
	}
	
}
