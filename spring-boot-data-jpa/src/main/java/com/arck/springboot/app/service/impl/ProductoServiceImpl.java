package com.arck.springboot.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arck.springboot.app.dao.IProducto;
import com.arck.springboot.app.models.entity.Producto;
import com.arck.springboot.app.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	private IProducto productoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByProducto(String term) {
		return productoDao.findByNombre(term);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

}
