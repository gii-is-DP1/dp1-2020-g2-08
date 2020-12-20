package org.springframework.samples.petclinic.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
	@NotNull
	@Column(name = "name")
	private String name;
	
	@NotNull
	@Column(name = "category")
	private String category;

	
	@NotNull
	@Column(name = "price")
	private Double price;
	
	@NotNull
	@Column(name = "inOffer")
	private String inOffer;
	
	
	

	

}
