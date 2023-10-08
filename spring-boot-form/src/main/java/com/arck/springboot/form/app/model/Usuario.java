package com.arck.springboot.form.app.model;

import java.util.Date;
import java.util.List;

import com.arck.springboot.form.app.validation.ApellidoRegex;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Usuario {

	private String identificador;

	@NotEmpty(message = "El Nombre no puede ser vacio")
//	@Pattern(regexp = "[0-9]{2}", message = "que te has columpiado chicoooooooo")
	private String nombre;

	@NotEmpty
	@ApellidoRegex
	private String apellido;

	@NotEmpty
	@Size(min = 3, max = 8)
	private String name;

	@NotEmpty
	private String password;

	@NotEmpty
	@Email(message = "El ormato del campo Email no es correcto")
	private String email;

	@NotNull
	@Min(3)
	@Max(5)
	private Integer cuenta;

	@NotNull
	private Pais pais;

	@NotEmpty
	private List<Role> roles;

	private Boolean habilitar;

	@NotEmpty
	private String genero;
	
	private String secreto;

	@NotNull
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCuenta() {
		return cuenta;
	}

	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Boolean getHabilitar() {
		return habilitar;
	}

	public void setHabilitar(Boolean habilitar) {
		this.habilitar = habilitar;
	}
	
	public String getSecreto() {
		return secreto;
	}

	public void setSecreto(String secreto) {
		this.secreto = secreto;
	}


	@Override
	public String toString() {
		return "Usuario [identificador=" + identificador + ", nombre=" + nombre + ", apellido=" + apellido + ", name="
				+ name + ", password=" + password + ", email=" + email + ", cuenta=" + cuenta + ", pais=" + pais
				+ ", fechaNacimiento=" + fechaNacimiento + "]";
	}

}
