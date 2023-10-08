package com.arck.springboot.di.app.models.entity;

public class ItemFactura {

	private Producto producto;
	private Integer cantidad;

	public ItemFactura(Producto producto, Integer cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "ItemFactura [producto=" + producto + ", cantidad=" + cantidad + "]";
	}
	
	public Long calcularImporte() {
		return this.producto.getPrecio()*this.cantidad;
	}

}
