package org.springframework.samples.petclinic.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;


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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getInOffer() {
		return inOffer;
	}

	public void setInOffer(String inOffer) {
		this.inOffer = inOffer;
	}
	
	
	

	

}
