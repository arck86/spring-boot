package com.arck.springboot.form.app.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.arck.springboot.form.app.model.Usuario;

@Component
public class UsuarioValidador implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Usuario.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Usuario usuario = (Usuario) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "NotEmpty.usuario.nombre");
		if(!usuario.getNombre().isEmpty() && usuario.getNombre().equals("Many")) {
			errors.rejectValue("nombre", "Nombre.not.many");
		}

	}

}
