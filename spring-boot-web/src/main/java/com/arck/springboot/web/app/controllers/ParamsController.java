package com.arck.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/params")
public class ParamsController {
	
	
	@GetMapping("/")
	public String index() {
		return "param/index";
	}

	@GetMapping("/string")
	public String param(@RequestParam(name = "texto",required=false, defaultValue = "Buenos dias") String texto, Model model) {
		model.addAttribute("resultado", texto);
		model.addAttribute("titulo", "Params");
		return "param/ver";
	}
	
	@GetMapping("/mix-params")
	public String param(@RequestParam String saludo, @RequestParam Integer numero, Model model) {
		model.addAttribute("resultado", "Saludo: '"+saludo+"' el numero: '"+numero+"'");
		model.addAttribute("titulo", "Params");
		return "param/ver";
	}
	
	@GetMapping("/mix-params-request")
	public String param(HttpServletRequest request, Model model) {
		String saludo = request.getParameter("saludo");
		Integer numero= null;
		try {
			numero = Integer.parseInt(request.getParameter("numero"));
		}catch (NumberFormatException e) {
			numero = 0;
		}
		model.addAttribute("resultado", "Saludo: '"+saludo+"' el numero: '"+numero+"'");
		model.addAttribute("titulo", "Params");
		return "param/ver";
	}
	
}
