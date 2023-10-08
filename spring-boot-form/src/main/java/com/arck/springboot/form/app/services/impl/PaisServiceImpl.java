package com.arck.springboot.form.app.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.arck.springboot.form.app.model.Pais;
import com.arck.springboot.form.app.services.PaisService;

@Service
public class PaisServiceImpl implements PaisService {
	
	private List<Pais> lista;

	public PaisServiceImpl() {
		lista = Arrays.asList(new Pais(1,"ES", "España"), 
				new Pais(2,"USA", "Estados Unidos"),
				new Pais(3,"ARG", "Argentina"),
				new Pais(4,"MEX", "Mexico"),
				new Pais(5,"PER", "Peru"),
				new Pais(6,"CUB", "CUBA"),
				new Pais(7,"POR", "Portugal"));
	}

	public List<Pais> listar(){
		return lista;
	}
	
	public Pais obtenerPorId(Integer id) {
		for(Pais item:lista) {
			if(item.getId()== id) {
				return item;
			}
		}
		return null;
	}
}
