package com.arck.springboot.form.app.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.arck.springboot.form.app.model.Role;
import com.arck.springboot.form.app.services.RoleService;

@Service
public class RoleServicesImpl implements RoleService {

	private List<Role> roles;
	
	public RoleServicesImpl() {
		roles = new ArrayList<>();
		roles.add(new Role(1, "Administrador", "ROLE_ADMIN"));
		roles.add(new Role(2, "Usuario", "ROLE_VIEW"));
		roles.add(new Role(3, "Creador", "ROLE_CREATE"));
		roles.add(new Role(4, "Moderador", "ROLE_UPDATE"));
	}

	@Override
	public List<Role> listar() {
		return roles;
	}

	@Override
	public Role obtenerPorId(Integer Id) {
		for(Role item: roles) {
			if(item.getId().equals(Id))
				return item;
		}
		return null;
	}

}
