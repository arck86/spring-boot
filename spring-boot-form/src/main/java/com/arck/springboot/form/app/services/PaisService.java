package com.arck.springboot.form.app.services;

import java.util.List;

import com.arck.springboot.form.app.model.Pais;

public interface PaisService {
	
	public List<Pais> listar();
	
	public Pais obtenerPorId(Integer Id);

}
