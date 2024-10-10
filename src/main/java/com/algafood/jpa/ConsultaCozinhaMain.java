package com.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algafood.AlgafoodApiApplication;
import com.algafood.domain.model.Cozinha;

public class ConsultaCozinhaMain {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
		
//		List<Cozinha> cozinhas = cadastroCozinha.listar();
//		
//		for (Cozinha cozinha : cozinhas) {
//			System.out.println(cozinha.getNome());
//		}
		
		
//		Cozinha cozinha1 = new Cozinha();
//		cozinha1.setNome("Brasileira");
//		
//		cadastroCozinha.adicionar(cozinha1);
		
		
//		Cozinha cozinha = cadastroCozinha.buscarPorId(1L);
//		
//		System.out.println(cozinha.getNome());
	}

}
