package com.arck.springboot.app.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value="logout", required = false)String logout, Model model,
			Principal principal,
			RedirectAttributes flash) {

		if (principal != null) {
			flash.addFlashAttribute("info", "Ya ha iniciado session anteriormente");
			return "redirect:/listar";
		}

		if (error != null) {
			model.addAttribute("error", "Error en el login");
		}
		
		if(logout!= null) {

			model.addAttribute("success", "Ha salido de la aplicación");
		}

		return "login";
	}

}
