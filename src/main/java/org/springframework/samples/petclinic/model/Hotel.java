package org.springframework.samples.petclinic.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
@Entity
@Table(name = "hotel")
public class Hotel extends BaseEntity{
	 @NotNull
	 @Column(name = "aforo")
	private Integer aforo;
	 
	 @NotNull
	 @Column(name = "ocupadas")
	private Integer ocupadas;
	 
	 
	 // lista de reservas del hotel
	@NotNull
	@OneToMany
	 @JoinColumn(name = "booking_id")	 
	private Set<Booking> bookings;

}
