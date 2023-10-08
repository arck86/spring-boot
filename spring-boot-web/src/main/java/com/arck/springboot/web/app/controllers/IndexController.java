package com.arck.springboot.web.app.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arck.springboot.web.app.models.Usuario;

@Controller
@RequestMapping("/app")
public class IndexController {
	
	@Value("${index.controller.titulo.index}")
	private String tituloIndex;
	
	@Value("${index.controller.titulo.perfil}")
	private String tituloPerfil;
	
	@Value("${index.controller.titulo.listar}")
	private String tituloListar;

	@GetMapping({"/index","/","/home"})
	public String index(Model model) {
		
		model.addAttribute("titulo", tituloIndex);
		return "index";
	}
	
	@RequestMapping("/perfil")
	public String perfil(Model model) {
		Usuario usuario = new Usuario();
		usuario.setNombre("Jesús");
		usuario.setApellido("Checa");
		usuario.setEmail("jcheca86@gmail.com");
		model.addAttribute("titulo", tituloPerfil);
		model.addAttribute("usuario", usuario);
		return "perfil";
	}
	
	@RequestMapping("/listar")
	public String listar(Model model) {
	
		model.addAttribute("titulo", tituloListar);
		
		return "listar";
	}
	
	@ModelAttribute("usuarios")
	public List<Usuario> poblarUsuarios(){
		return Arrays.asList(new Usuario("Jesus","Checa","jcheca86@gmail.com"),
				new Usuario("Ivan","Gonzalez","ivangon@gmail.com"),
				new Usuario("Bea","Garcia","beagar@gmail.com"),
				new Usuario("julian","vieco","julivie@gmail.com"));
		
	}
}
