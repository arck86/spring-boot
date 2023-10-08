package com.arck.springboot.form.app.model;

import java.util.Objects;

public class Role {

	private Integer id;
	private String nombre;
	private String rol;
	
	public Role() {
		
	}

	public Role(Integer id, String nombre, String rol) {
		this.id = id;
		this.nombre = nombre;
		this.rol = rol;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", nombre=" + nombre + ", rol=" + rol + "]";
	}
	
	
}
