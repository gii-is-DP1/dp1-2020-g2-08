package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity
@Table(name = "producto_vendido")
public class ProductoVendido extends BaseEntity{
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	@Column(name = "precio")
	private Double precio;
	
	@Column(name = "nombre")
	private String nombre;
	
	
	
	
	@ManyToOne
	@JoinColumn
	private Order order;
	
	public double getTotal() {
		return this.precio*this.cantidad;
	}
}
