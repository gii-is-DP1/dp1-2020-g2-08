package org.springframework.samples.petclinic.model;





public class ProductoParaVenta extends Product{

	
	private Integer cantidad;
	
	
	
	
	 public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public void aumentarCantidad() {
		  this.cantidad++;
	 }
	public double getTotal() {
		return this.getPrice()*this.getCantidad();
	}
	
	
	
}
