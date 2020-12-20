package org.springframework.samples.petclinic.model;



import lombok.Data;

@Data

public class ProductoParaVenta extends Product{

	
	private Integer cantidad;
	
	
	 public void aumentarCantidad() {
		  this.cantidad++;
	 }
	public double getTotal() {
		return this.getPrice()*this.getCantidad();
	}
	
	
	
}
