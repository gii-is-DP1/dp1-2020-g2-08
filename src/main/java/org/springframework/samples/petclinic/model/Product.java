package org.springframework.samples.petclinic.model;



import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;


import org.springframework.samples.petclinic.service.ProductService;

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
	@Digits(fraction = 0, integer = 10000)
	@Column(name = "price")
	private Double price;
	
	@NotNull
	@Column(name = "inOffer")
	private String inOffer;
	
	@Transient
	private Double average;
	
	@Version
	private Integer version;
	

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.LAZY)	
	private Set<ProductReview> reviews;

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

	public Set<ProductReview> getReviews() {
		return reviews;
	}

	public void setReviews(Set<ProductReview> reviews) {
		this.reviews = reviews;
	}

	public Double getAverage() {
		
		if(this.getReviews().size()==0) {
			return 0.0;
		}else {
		Double average = this.getReviews().stream().mapToDouble(x->x.getStars()).average().getAsDouble();
		return average;
		}
	}

	public void setAverage(Double average) {
		this.average = average;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	

}
