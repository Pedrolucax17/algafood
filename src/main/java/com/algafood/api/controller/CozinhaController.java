package com.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.domain.model.Cozinha;
import com.algafood.domain.repository.CozinhaRepository;
import com.algafood.exceptions.EntidadeEmUsoException;
import com.algafood.exceptions.EntidadeNaoEncontradaException;
import com.algafood.service.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository repository;
	
	@Autowired
	private CozinhaService service;
	
	
	@GetMapping
	public List<Cozinha> listar(){ 
		return repository.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
		Cozinha cozinha = repository.buscar(id);
		
		if(cozinha != null) {
			return ResponseEntity.ok(repository.buscar(id));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return service.salvar(cozinha);
	}
	
	@PutMapping("/id/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = repository.buscar(id);
		
		if(cozinha != null) {
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			
			service.salvar(cozinhaAtual);
			
			return ResponseEntity.ok(cozinhaAtual);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/id/{id}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long id){
		try {
			service.remover(id);
			return ResponseEntity.noContent().build();
		}
		catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
		catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
