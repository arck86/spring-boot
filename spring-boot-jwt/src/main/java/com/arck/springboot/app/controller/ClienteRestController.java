package com.arck.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.arck.springboot.app.service.ClienteService;
import com.arck.springboot.app.view.xml.ClienteList;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {
	
	@Autowired
	ClienteService clienteService;

	@GetMapping("/listar")
	@Secured("ROLE_ADMIN")
	public  @ResponseBody ClienteList listarRest() {
		
		return new ClienteList(clienteService.findAll());
	}
}
