package com.arck.springboot.form.app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ApellidoRegexValidador implements ConstraintValidator<ApellidoRegex, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(!value.isEmpty() && value.equals("Many")) {
			return false;
		}
		return true;
	}

}
