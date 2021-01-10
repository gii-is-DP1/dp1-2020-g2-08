package org.springframework.samples.petclinic.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product_reviews")
public class ProductReview extends BaseEntity{

	@NotNull
	@Column(name = "stars")
	private Integer stars;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	 private Client client;
	
	@ManyToOne
	@JoinColumn(name = "productVend_id")
	 private ProductoVendido productoVendido;
	
//	@ManyToOne
//	@JoinColumn(name = "product")
//	 private Product product;

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ProductoVendido getProductoVendido() {
		return productoVendido;
	}

	public void setProductoVendido(ProductoVendido productoVendido) {
		this.productoVendido = productoVendido;
	}

//	public Product getProduct() {
//		return product;
//	}
//
//	public void setProduct(Product product) {
//		this.product = product;
//	}

}
