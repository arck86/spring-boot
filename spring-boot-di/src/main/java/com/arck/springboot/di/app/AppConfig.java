package com.arck.springboot.di.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.arck.springboot.di.app.models.entity.ItemFactura;
import com.arck.springboot.di.app.models.entity.Producto;

@Configuration
public class AppConfig {

	@Bean("listItemFactura")
	public List<ItemFactura> listItemFactura() {
		Producto producto = new Producto("Portatil", 1000L);
		ItemFactura item = new ItemFactura(producto, 1);
		
		Producto producto2 = new Producto("Auriculares", 150L);
		ItemFactura item2 = new ItemFactura(producto2, 2);
		
		Producto producto3 = new Producto("Raton", 90L);
		ItemFactura item3 = new ItemFactura(producto3, 1);
		
		return Arrays.asList(item,item2,item3);
	}
	
	@Bean("listItemFacturaOficina")
	@Primary
	public List<ItemFactura> resgistrarItemsOficina() {
		Producto producto = new Producto("monitor", 250L);
		ItemFactura item = new ItemFactura(producto, 2);
		
		Producto producto2 = new Producto("portatil Asus", 800L);
		ItemFactura item2 = new ItemFactura(producto2, 1);
		
		Producto producto3 = new Producto("Impresora", 250L);
		ItemFactura item3 = new ItemFactura(producto3, 1);
		
		Producto producto4 = new Producto("Escritorio ", 150L);
		ItemFactura item4 = new ItemFactura(producto4, 1);
		
		return Arrays.asList(item,item2,item3,item4);
	}
}
