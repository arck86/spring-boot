package com.arck.springboot.app.service;

import java.util.List;

import com.arck.springboot.app.models.entity.Cliente;

public interface ClienteService {
	
	public List<Cliente> findAll();
	
	public void save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public void eliminar(Long id);

	public Cliente fetchByIdWithFacturasAndItemsAndProductos(Long id);

}
