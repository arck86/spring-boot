package com.arck.springboot.app.service;

import com.arck.springboot.app.models.entity.Factura;

public interface FacturaService {
	
	public void saveFactura(Factura factura);
	
	public Factura FindFacturaById(Long id);
	
	public void deleteFactura(Long id);

	public Factura fetchByIdWithClienteAndItemAdProducto(long id);

}
