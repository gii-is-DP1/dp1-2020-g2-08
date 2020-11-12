package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
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

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "hotel")
public class Hotel extends BaseEntity {

	@NotNull
	@Column(name = "aforo")
	private Integer aforo;

	@NotNull
	@Column(name = "ocupadas")
	private Integer ocupadas;
	
	
//	@Column(name = "dias_llenos")
//	@DateTimeFormat(pattern = "yyyy/MM/dd")
//	private LocalDate diasLlenos;

	// lista de reservas del hotel
	@OneToMany
	@JoinColumn(name = "booking_id")
	private Set<Booking> bookings;

	// lista de reviews del hotel
	@OneToMany
	@JoinColumn(name = "review_id")
	private Set<Review> reviews;

}
