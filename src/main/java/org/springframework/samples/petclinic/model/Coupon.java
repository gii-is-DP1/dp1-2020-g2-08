package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;




@Entity
@Table(name = "coupons")
public class Coupon extends BaseEntity{
	
	//porcentaje de descuento del cupon(de 0 a 100)
	@Column(name = "discount")
	private Integer discount;
	
	@Column(name = "expireDate")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate expireDate;
//	
//	 @ManyToMany(mappedBy = "coupons", fetch = FetchType.LAZY)
//	   private Set<Client> clients = new HashSet<>();

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public LocalDate getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}

//	public Set<Client> getClients() {
//		return clients;
//	}
//
//	public void setClients(Set<Client> clients) {
//		this.clients = clients;
//	}
//
//	
	
	

}
