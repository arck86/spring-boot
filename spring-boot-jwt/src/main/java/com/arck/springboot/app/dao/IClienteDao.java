package com.arck.springboot.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.arck.springboot.app.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{
	
	@Query("select c from Cliente c left join fetch c.facturas f where c.id = ?1")
	public Cliente fetchByIdWithFacturasAndItemsAndProductos(Long id);

}
	