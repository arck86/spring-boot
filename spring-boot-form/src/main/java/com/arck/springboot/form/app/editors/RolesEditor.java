package com.arck.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arck.springboot.form.app.services.RoleService;

@Component
public class RolesEditor extends PropertyEditorSupport {
	
	@Autowired
	private RoleService roleService;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			setValue(roleService.obtenerPorId(Integer.valueOf(text)));
		}catch (NumberFormatException e) {
			setValue(null);
		}
	}

}
