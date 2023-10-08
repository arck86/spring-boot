package com.arck.springboot.di.app.models.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class Factura {

	@Value("${factura.descripcion}")
	private String descripcion;

	@Autowired
	private Cliente cliente;
	
	@Autowired
	private List<ItemFactura> items;
	
	@PostConstruct
	public void inicializar() {
		this.cliente.setApellido(this.cliente.getApellido()+" Rodríguez");
	}
	
	@PreDestroy
	public void destruir() {
		System.out.println("Salimos del la pantalla");
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Factura [descripcion=" + descripcion + ", cliente=" + cliente + ", items=" + items + "]";
	}

}
