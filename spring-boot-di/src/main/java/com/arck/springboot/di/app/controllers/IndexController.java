package com.arck.springboot.di.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arck.springboot.di.app.service.IService;

@Controller
public class IndexController {
	
	@Autowired
	@Qualifier("simple")
	private IService miService;
	
	@RequestMapping("/index")
	public String index(Model model) {
		
		String nombre = miService.miProceso();
		model.addAttribute("proceso", nombre);
		model.addAttribute("titulo", "Index");
		
		return "index";
	}

	public void setMiService(IService miService) {
		this.miService = miService;
	}
	
	

}
