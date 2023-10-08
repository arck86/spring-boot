package com.arck.springboot.error.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.arck.springboot.error.app.models.domain.Usuario;
import com.arck.springboot.error.app.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private List<Usuario> lista;
	
	public UsuarioServiceImpl() {
		lista = new ArrayList<>();
		lista.add(new Usuario(1, "Jesus", "Checa"));
		lista.add(new Usuario(2, "Beatriz", "Garcia"));
		lista.add(new Usuario(3, "Raquel", "Checa"));
		lista.add(new Usuario(4, "Juan", "Perez"));
		lista.add(new Usuario(5, "Miguel", "Gonzalez"));
		lista.add(new Usuario(6, "Fran", "Cuesta"));
		lista.add(new Usuario(7, "Victoria", "Abril"));
	}
	
	@Override
	public List<Usuario> listar() {
		return this.lista;
	}

	@Override
	public Usuario obtenerPorId(Integer id) {
		Usuario resultado = null;
		for(Usuario item: this.lista){
			if(item.getId().equals(id)) {
				resultado = item;
				break;
			}
		}
		return resultado;
	}

	@Override
	public Optional<Usuario> obtenerPorIdOptional(Integer id) {
		Usuario usuario = obtenerPorId(id);
		return Optional.ofNullable(usuario);
	}

}
