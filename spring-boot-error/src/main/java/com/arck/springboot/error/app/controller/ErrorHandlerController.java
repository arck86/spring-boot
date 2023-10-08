package com.arck.springboot.error.app.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.arck.springboot.error.app.exception.UsuarioNoEncontradoException;

@ControllerAdvice
public class ErrorHandlerController {

	@ExceptionHandler(ArithmeticException.class)
	public String aritmeticaError(ArithmeticException ex, Model model) {
		model.addAttribute("error", "error de Arithmetic");
		model.addAttribute("message", ex.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());
		
		return "error/aritmetica";
	}
	
	@ExceptionHandler(NumberFormatException.class)
	public String aritmeticaError(NumberFormatException ex, Model model) {
		model.addAttribute("error", "Error de Number Format");
		model.addAttribute("message", ex.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());
		
		return "error/numeroFormato";
	}
	
	@ExceptionHandler(UsuarioNoEncontradoException.class)
	public String usuarioError(UsuarioNoEncontradoException ex, Model model) {
		model.addAttribute("error", "Error de Usuario no encontrado");
		model.addAttribute("message", ex.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());
		
		return "error/usuarioError";
	}
}
