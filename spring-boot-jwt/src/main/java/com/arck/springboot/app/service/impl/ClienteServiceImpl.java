package com.arck.springboot.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arck.springboot.app.dao.IClienteDao;
import com.arck.springboot.app.models.entity.Cliente;
import com.arck.springboot.app.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	public Cliente fetchByIdWithFacturasAndItemsAndProductos(Long id) {
		return clienteDao.fetchByIdWithFacturasAndItemsAndProductos(id);
	}
}
