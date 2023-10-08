package com.arck.springboot.app.service;

import java.util.List;

import com.arck.springboot.app.models.entity.Producto;

public interface ProductoService {
	
	public List<Producto> findByProducto(String term);

	public Producto findProductoById(Long id);

}
