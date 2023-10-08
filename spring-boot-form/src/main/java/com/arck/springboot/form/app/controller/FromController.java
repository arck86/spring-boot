package com.arck.springboot.form.app.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.arck.springboot.form.app.editors.NombreMayusculaEditor;
import com.arck.springboot.form.app.editors.PaisPropertyEditor;
import com.arck.springboot.form.app.editors.RolesEditor;
import com.arck.springboot.form.app.model.Pais;
import com.arck.springboot.form.app.model.Role;
import com.arck.springboot.form.app.model.Usuario;
import com.arck.springboot.form.app.services.PaisService;
import com.arck.springboot.form.app.services.RoleService;
import com.arck.springboot.form.app.validation.UsuarioValidador;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("usuario")
public class FromController {
	
	@Autowired
	private UsuarioValidador usuarioValidador;
	
	@Autowired
	private PaisService paisService;
	
	@Autowired
	private PaisPropertyEditor paisPropertyEditor;
	
	@Autowired
	private RolesEditor rolesEditor;
	
	@Autowired
	private RoleService roleService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(usuarioValidador);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(format, false));
		binder.registerCustomEditor(String.class, "genero", new NombreMayusculaEditor());
		
		binder.registerCustomEditor(Pais.class, "pais", paisPropertyEditor);
		binder.registerCustomEditor(Role.class, "roles", rolesEditor);
	}
	
	@ModelAttribute("listaPaises")
	public List<Pais> listaPaises(){
		return paisService.listar();
	}
	
	@ModelAttribute("listaRolesList")
	public List<String> listaRolesList(){
		List<String> roles = new ArrayList<>();
		roles.add("ROLES_ADMIN");
		roles.add("ROLES_VIEW");
		roles.add("ROLES_UPDATE");
		roles.add("ROLES_CREATE");
		
		return roles;
	}
	
	@ModelAttribute("listaRolesMap")
	public Map<String,String> listaRolesMap(){
		Map<String,String> roles = new HashMap<>();
		roles.put("ROLES_ADMIN","Administrador");
		roles.put("ROLES_VIEW","Usuario");
		roles.put("ROLES_UPDATE","Moderador");
		roles.put("ROLES_CREATE","Creador");
		
		return roles;
	}
	
	@ModelAttribute("listaRoles")
	public List<Role> listaRoles(){
		return roleService.listar();
	}
	
	@ModelAttribute("paisesMap")
	public Map<String,String> paises(){
		Map<String,String> paises = new HashMap<>();
		paises.put("ES", "España");
		paises.put("USA", "Estados Unidos");
		paises.put("ARG", "Argentina");
		paises.put("MEX", "Mexico");
		paises.put("PER", "Peru");
		paises.put("CUB", "CUBA");
		paises.put("POR", "Portugal");
		return paises;
	}
	@ModelAttribute("generos")
	public List<String> listGeneros(){
		return Arrays.asList("Hombre","Mujer");
	}
	
	
	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setIdentificador("4");
		usuario.setNombre("Jesus");
		usuario.setApellido("Checa");
		usuario.setHabilitar(Boolean.TRUE);
		usuario.setSecreto("Esto es un secreto");
		usuario.setPais(new Pais(1,"ES", "España"));
		usuario.setRoles(Arrays.asList(new Role(1, "Administrador", "ROLE_ADMIN")));
		model.addAttribute("usuario", usuario);
		
		model.addAttribute("titulo", "Formulario Usuario");
		return "form";
	}
	
	@PostMapping("/form")
	public String procesar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model, SessionStatus status) {
		
//		usuarioValidador.validate(usuario, result);
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario Usuario");
			return "form";
		}
		
		return "redirect:/ver";
	}
	
	@GetMapping("/ver")
	public String ver(@SessionAttribute(name="usuario", required = false) Usuario usuario,Model model, SessionStatus status) {
		if(usuario == null) {
			return "redirect:/form";
		}
		model.addAttribute("titulo", "Formulario Usuario");

		status.setComplete();
		return "resultado";
	}

}
