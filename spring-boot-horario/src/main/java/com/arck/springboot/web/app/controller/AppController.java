package com.arck.springboot.web.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
	
	@Value("${config.horario.apertura}")
	private Integer apertura;
	
	@Value("${config.horario.cierre}")
	private Integer cierre;
	
	@GetMapping({"/","/index"})
	public String index(Model model) {
		model.addAttribute("Titulo", "Horario");
		return "index";
	}
	
	@GetMapping({"/cerrado"})
	public String cerrado(Model model) {
		model.addAttribute("Titulo", "Cerrado");
		StringBuilder error = new StringBuilder("Error, por lo que cerramos la aplicación, si quiere algo visitenos entre las ")
				.append(apertura).append(" horas y las ").append(cierre).append(" horas.");
		model.addAttribute("error", error.toString());
		return "error";
	}

}
