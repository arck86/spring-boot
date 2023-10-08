package com.arck.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "forward:/app/index";
	}
	
	@RequestMapping("/google")
	public String google() {
		return "redirect:http://www.google.com";
	}
	
	
	
}
