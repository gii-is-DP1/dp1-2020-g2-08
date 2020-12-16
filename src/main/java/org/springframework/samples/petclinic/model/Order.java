package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;


@Entity

@Table(name = "orders")
public class Order extends BaseEntity{
	// fecha de compra del pedido
	 @Column(name = "order_date")
		@DateTimeFormat(pattern = "yyyy/MM/dd")
	    private LocalDate orderDate;
	 
	// fecha de estimacion de entrega
		 @Column(name = "delivery_date")
			@DateTimeFormat(pattern = "yyyy/MM/dd")
		    private LocalDate deliveryDate;
		 
		 //Importe total del producto
		 @Column(name = "price_order")
		 private Double priceOrder;
		 
		 
		 //Estado del producto
		 @Column(name = "state")
		 private String state;
		 
		 @Column(name = "address")
		 private String address;
		 
		 @Column(name = "city")
		 private String city;
		 
		 @Column(name = "postalCode")
		 private Integer postalCode;
		 
		
		 
		 @Column(name = "country")
		 private String country;
		 
	
		 @ManyToOne
		 @JoinColumn(name = "client_id")
		 private Client client;
		 
		 @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)	
			private Set<Product> products;

		public LocalDate getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(LocalDate orderDate) {
			this.orderDate = orderDate;
		}

		public LocalDate getDeliveryDate() {
			return deliveryDate;
		}

		public void setDeliveryDate(LocalDate deliveryDate) {
			this.deliveryDate = deliveryDate;
		}

		public Double getPriceOrder() {
			return priceOrder;
		}

		public void setPriceOrder(Double priceOrder) {
			this.priceOrder = priceOrder;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public Integer getPostalCode() {
			return postalCode;
		}

		public void setPostalCode(Integer postalCode) {
			this.postalCode = postalCode;
		}

	

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public Client getClient() {
			return client;
		}

		public void setClient(Client client) {
			this.client = client;
		}

		public Set<Product> getProducts() {
			return products;
		}

		public void setProducts(Set<Product> products) {
			this.products = products;
		}
		 
		 
		 
		 
		 
		 
		 
		 
		 
}
