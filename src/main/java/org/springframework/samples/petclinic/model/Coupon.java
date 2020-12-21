package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "coupons")
public class Coupon extends BaseEntity{
	
	//porcentaje de descuento del cupon(de 0 a 100)
	@Column(name = "discount")
	private Integer discount;
	
	@Column(name = "expireDate")
	private LocalDate expireDate;
	
	 @ManyToMany(mappedBy = "coupons", fetch = FetchType.LAZY)
	   private Set<Client> students = new HashSet<>();
	
	

}
