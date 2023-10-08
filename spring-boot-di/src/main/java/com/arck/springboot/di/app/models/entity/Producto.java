package com.arck.springboot.di.app.models.entity;

public class Producto {

	private String nombre;
	private Long precio;
	
	public Producto(String nombre, Long precio) {
		this.nombre = nombre;
		this.precio = precio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getPrecio() {
		return precio;
	}
	public void setPrecio(Long precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto [nombre=" + nombre + ", precio=" + precio + "]";
	}
	
}
