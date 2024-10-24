package com.algafood.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.algafood.domain.model.Permissao;
import com.algafood.domain.repository.PermissaoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository{
	
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Permissao> listar() {
		return manager.createQuery("from FormaPagamento", Permissao.class)
				.getResultList();
	}
	
	@Override
	public Permissao buscar(Long id) {
		return manager.find(Permissao.class, id);
	}
	
	@Transactional
	@Override
	public Permissao salvar(Permissao permissao) {
		return manager.merge(permissao);
	}
	
	@Transactional
	@Override
	public void remover(Permissao permissao) {
		permissao = buscar(permissao.getId());
		manager.remove(permissao);
	}

}