package com.arck.springboot.error.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.arck.springboot.error.app.exception.UsuarioNoEncontradoException;
import com.arck.springboot.error.app.models.domain.Usuario;
import com.arck.springboot.error.app.service.UsuarioService;

@Controller
public class ErrorController {
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/index")
	public String index() {
		
		@SuppressWarnings("unused")
//		Integer valor = 100/0;
		Integer valor = Integer.parseInt("10x");
		return "index";
	}
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Integer id, Model model) {
//		Usuario usuario = usuarioService.obtenerPorIdOp(id);
		Usuario usuario = usuarioService.obtenerPorIdOptional(id).orElseThrow(() ->new UsuarioNoEncontradoException(id.toString()));
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Detalle usuario: "+ usuario.getNombre());
		return "ver";
	}
}
