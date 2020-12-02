package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;


@Entity

@Table(name = "bookings")
public class Booking extends BaseEntity {
	
	//Fecha de inicio de la reserva en el hotel.
    @NotNull
    @Column(name = "start_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;
 
    
    //Fecha de fin de la reserva en el hotel.
    @NotNull
    @Column(name = "end_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate endDate;
	
    //Mascota
    @ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
    
  //Owner
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
    
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
    
    
    
    
    
}
