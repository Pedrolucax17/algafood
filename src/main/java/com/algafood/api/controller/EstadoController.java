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

import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.EstadoRepository;
import com.algafood.exceptions.EntidadeEmUsoException;
import com.algafood.exceptions.EntidadeNaoEncontradaException;
import com.algafood.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EstadoService estadoService;
	
	
	@GetMapping
	public List<Estado> listar(){
		return estadoRepository.listar();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Estado> buscar(@PathVariable Long id) {
		Estado estado = estadoRepository.buscar(id);
		
		if(estado != null) {
			return ResponseEntity.ok(estado);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Estado salvar(@RequestBody Estado estado){
		return estadoService.salvar(estado);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Estado> atualizar(@RequestBody Estado estado, @PathVariable Long id) {
		Estado estadoAtual = estadoRepository.buscar(id);
		
		if(estadoAtual != null) {
			BeanUtils.copyProperties(estado, estadoAtual, "id");
			
			estadoService.salvar(estadoAtual);
			
			return ResponseEntity.ok(estadoAtual);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Estado> remover(@PathVariable Long id) {
		try {
			estadoService.remover(id);
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
