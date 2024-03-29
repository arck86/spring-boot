package com.arck.springboot.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.arck.springboot.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
	
	public Usuario findByUsername(String username);

}
