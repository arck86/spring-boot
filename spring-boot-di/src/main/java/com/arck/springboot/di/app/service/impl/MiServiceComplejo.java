package com.arck.springboot.di.app.service.impl;

import org.springframework.stereotype.Service;

import com.arck.springboot.di.app.service.IService;

@Service("complejo")
public class MiServiceComplejo implements IService{
	
	public String miProceso() {
		return "ejecutando algun proceso aun mas complicado........";
	}

}
