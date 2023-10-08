package com.arck.springboot.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arck.springboot.app.dao.IFactura;
import com.arck.springboot.app.models.entity.Factura;
import com.arck.springboot.app.models.entity.Producto;
import com.arck.springboot.app.service.FacturaService;

@Service
public class FacturaServiceImpl implements FacturaService{

	@Autowired
	private IFactura facturaDao; 
	
	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
		
	}

	@Override
	@Transactional
	public Factura FindFacturaById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}
	

	@Override
	@Transactional
	public void deleteFactura(Long id) {
		facturaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura fetchByIdWithClienteAndItemAdProducto(long id) {
		return facturaDao.fetchByIdWithClienteAndItemAdProducto(id);
	}

}
