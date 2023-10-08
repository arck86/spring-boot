package com.arck.springboot.form.app.services;

import java.util.List;

import com.arck.springboot.form.app.model.Role;

public interface RoleService {
	
	public List<Role> listar();
	
	public Role obtenerPorId(Integer Id);
}
