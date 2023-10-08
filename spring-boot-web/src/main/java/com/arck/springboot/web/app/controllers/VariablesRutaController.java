package com.arck.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/variables")
public class VariablesRutaController {

	@GetMapping("/")
	public String index(Model model){
		model.addAttribute("titulo", "Variables index");
		return "variables/index";
	}
	
	@GetMapping("/string/{texto}")
	public String variables(@PathVariable String texto, Model model) {
		model.addAttribute("resultado", texto);
		model.addAttribute("titulo", "Variables");
		return "variables/ver";
	}
	
	@GetMapping("/string/{texto}/{numero}")
	public String variasVariables(@PathVariable String texto,@PathVariable Integer numero, Model model) {
		model.addAttribute("resultado","Texto: "+ texto+", numero: "+numero);
		model.addAttribute("titulo", "Variables");
		return "variables/ver";
	}
}
