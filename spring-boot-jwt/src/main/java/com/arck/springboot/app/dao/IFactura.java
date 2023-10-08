package com.arck.springboot.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.arck.springboot.app.models.entity.Factura;

public interface IFactura extends CrudRepository<Factura, Long> {
	
	@Query("select f from Factura f join fetch f.cliente c join fetch f.items i join fetch i.producto p where f.id =?1")
	public Factura fetchByIdWithClienteAndItemAdProducto(Long id);

}
