package com.arck.springboot.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arck.springboot.app.models.entity.Cliente;
import com.arck.springboot.app.models.entity.Factura;
import com.arck.springboot.app.models.entity.Item;
import com.arck.springboot.app.models.entity.Producto;
import com.arck.springboot.app.service.ClienteService;
import com.arck.springboot.app.service.FacturaService;
import com.arck.springboot.app.service.ProductoService;

import jakarta.validation.Valid;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ProductoService productoService;

	@Autowired
	private FacturaService facturaService;

	private final Logger log = LoggerFactory.getLogger(FacturaController.class);

	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable Long clienteId, Model model, RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(clienteId);

		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}

		Factura factura = new Factura();

		factura.setCliente(cliente);

		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Crear factura");

		return "factura/form";
	}

	@GetMapping(value = "/carga-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		return productoService.findByProducto(term);
	}

	@PostMapping("/form")
	public String guardar(@Valid Factura factura, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, RedirectAttributes flash,
			SessionStatus status) {
		if (result.hasErrors()) {

			model.addAttribute("titulo", "Crear Cliente");
			return "factura/form";
		}
		
		if(itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Cliente");
			model.addAttribute("error", "La factura necesita productos");
			return "factura/form";
		}

		for (int i = 0; i < itemId.length; i++) {
			Producto producto = productoService.findProductoById(itemId[i]);
			Item item = new Item();
			item.setCantidad(cantidad[i]);
			item.setProducto(producto);
			factura.addItems(item);

			log.debug("ID: " + itemId[i] + " cantidad: " + cantidad[i]);
		}

		facturaService.saveFactura(factura);
		status.setComplete();

		flash.addFlashAttribute("success", "Factura creada con exito");
		return "redirect:/ver/" + factura.getCliente().getId();
	}
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Factura factura = facturaService.fetchByIdWithClienteAndItemAdProducto(id);
		if(factura == null) {
			model.addAttribute("error", "La factura no existe");
			return "redirect:/listar";
		}
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Factura: "+factura.getDescripcion());
		return "factura/ver";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Factura factura = facturaService.FindFacturaById(id);
		if(factura == null) {
			model.addAttribute("error", "La factura no existe");
			return "redirect:/listar";
		}
		facturaService.deleteFactura(id);
		flash.addFlashAttribute("success", "Factura eliminada con exito");
		
		return "redirect:/ver/" + factura.getCliente().getId();
	}
	
}
