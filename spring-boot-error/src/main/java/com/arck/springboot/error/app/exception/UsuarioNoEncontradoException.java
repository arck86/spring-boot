package com.arck.springboot.error.app.exception;

public class UsuarioNoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = -1374932142704814009L;

	public UsuarioNoEncontradoException(String id) {
		super("Usuario con ID: "+id+" no existe en el sistema.");
		// TODO Auto-generated constructor stub
	}

	
}
