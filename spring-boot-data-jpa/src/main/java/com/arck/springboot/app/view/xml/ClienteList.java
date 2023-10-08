package com.arck.springboot.app.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.arck.springboot.app.models.entity.Cliente;



@XmlRootElement
public class ClienteList {

	public List<Cliente> clientes;

	public ClienteList() {
	}

	public ClienteList(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
		
	
}
