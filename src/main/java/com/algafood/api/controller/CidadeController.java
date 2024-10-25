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
import org.springframework.web.bind.annotation.RestController;

import com.algafood.domain.model.Cidade;
import com.algafood.domain.repository.CidadeRepository;
import com.algafood.exceptions.EntidadeEmUsoException;
import com.algafood.exceptions.EntidadeNaoEncontradaException;
import com.algafood.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepository repository;
	
	@Autowired
	private CidadeService service;
	
	@GetMapping
	public List<Cidade> listar(){
		return repository.listar();
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long id) {
		Cidade cidade = repository.buscar(id);
		if(cidade != null) {
			return ResponseEntity.ok(cidade);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Cidade cidade) {
		try {
			
			cidade = service.salvar(cidade);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
			
		}
		catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@RequestBody Cidade cidade, @PathVariable Long id){
		try {
			
			Cidade cidadeAtual = repository.buscar(id);
			
			if(cidadeAtual != null) {
				BeanUtils.copyProperties(cidade, cidadeAtual, "id");
				
				cidadeAtual = repository.salvar(cidade);
				
				return ResponseEntity.ok(cidade);
			}
			
			return ResponseEntity.notFound().build();
			
		}
		catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cidade> remover(@PathVariable Long id){
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
