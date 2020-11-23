package org.springframework.samples.petclinic.model;


import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "shop")
public class Shop extends BaseEntity {
	
	@NotNull
	@OneToMany
	@JoinColumn(name = "product_id")
	private Set<Product> products;
	
	@NotNull
	private String category;

}
