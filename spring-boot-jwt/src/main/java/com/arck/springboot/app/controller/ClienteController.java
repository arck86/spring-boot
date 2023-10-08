package com.arck.springboot.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arck.springboot.app.models.entity.Cliente;
import com.arck.springboot.app.service.ClienteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	private MessageSource messageSource;
	
	
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	@GetMapping({"/listar","/"})
	public String listar(Model model, HttpServletRequest request, Locale locale) {
		
		if(hasRole("ROLE_USER")) {
			logger.info("El usuario tiene permisos de USER");
		}else {
			logger.info("Aqui no deberías estar");
		}
		
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
		if(securityContext.isUserInRole("USER")){
			logger.info("Mirando de una forma diferente si el usuario tiene permisos de USER");
		}
		if(request.isUserInRole("ROLE_USER")){
			logger.info("Mirando de una forma diferente (HttpServletRequest) si el usuario tiene permisos de USER");
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("El usuario autentificado es: "+auth.getName());
		
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		model.addAttribute("clientes", clienteService.findAll());
		return "listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/form")
	public String crear(Model model) {
		
		Cliente cliente = new Cliente();
		
		model.addAttribute("titulo", "Crear Cliente");
		model.addAttribute("cliente", cliente);
		return "form";
	}
	
	@Secured("ROLE_USER")
	@GetMapping("/ver/{id}")
	public String ver (@PathVariable Long id, Model model, RedirectAttributes flash ) {
		
		Cliente cliente = clienteService.fetchByIdWithFacturasAndItemsAndProductos(id);
		if(cliente == null) {
			flash.addFlashAttribute("error", "El cliete no exixte en la base de datos");
			return "redirect:/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Detalle cliente: "+cliente.getNombre());
		
		return "ver";
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/form")
	public String crear(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash ,SessionStatus status) {
		if(result.hasErrors()) {
			flash.addFlashAttribute("success", "El cliente no es correcto");

			model.addAttribute("titulo", "Crear Cliente");
			return "form";
		}
		
		if(!foto.isEmpty()) {
			String rootPath = "C://Temp//uploads";
			try {
				byte[] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(rootPath+"//"+foto.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info", "Se ha subido correctamente la foto: "+foto.getOriginalFilename());
				
				cliente.setFoto(foto.getOriginalFilename());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		clienteService.save(cliente);
		flash.addFlashAttribute("success", "Cliente creado con exito");
		status.setComplete();
		return "redirect:listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/form/{id}")
	public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
		
		Cliente cliente = clienteService.findOne(id);
		
		if(cliente==null) {

			flash.addFlashAttribute("error", "El cliente no es existe");
			return "redirect:/listar";
		}
		
		model.addAttribute("titulo", "Actualizar Cliente");
		model.addAttribute("cliente", cliente);
		return "form";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
		if(id>0) {
			clienteService.eliminar(id);
		}

		flash.addFlashAttribute("success", "Cliente eliminado");
		return "redirect:/listar";
		
	}
	
	private Boolean hasRole(String rol) {
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if(securityContext== null) {
			return false;
		}

		Authentication auth = securityContext.getAuthentication();
		if(auth == null) {
			return false;
		}
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		
//		return roles.contains(new SimpleGrantedAuthority(rol));
		for(GrantedAuthority item: roles) {
			if(rol.equals(item.getAuthority())) {
				return true;
			}
		}
		return false;
	}
}
