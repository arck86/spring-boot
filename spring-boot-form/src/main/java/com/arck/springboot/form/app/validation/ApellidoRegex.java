package com.arck.springboot.form.app.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ApellidoRegexValidador.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ApellidoRegex {
	
	String message() default "{Apellido.validate.custom}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
