package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

	@Entity

	@Table(name = "adoptions")
	public class Adoption extends BaseEntity {
		
		//Fecha de inicio de la adopcion 
	   
	    @Column(name = "start_date")
		@DateTimeFormat(pattern = "yyyy/MM/dd")
	    private LocalDate startDate;
	 
	    
	    //Fecha de fin de la adopcion 
	   
	    @Column(name = "end_date")
		@DateTimeFormat(pattern = "yyyy/MM/dd")
	    private LocalDate endDate;
		
	    //Animal
	    @ManyToOne
		@JoinColumn(name = "animal_id")
		private Pet pet;
	    
	  //Administrador
	    @ManyToOne
	    @JoinColumn(name = "owner_id")
	    private Owner owner;
	    
	    @ManyToOne
	    @JoinColumn(name = "shelder_id")
	    private Shelter shelter;

		public LocalDate getStartDate() {
			return this.startDate;
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

		public Shelter getShelter() {
			return shelter;
		}

		public void setshelter(Shelter shelter) {
			this.shelter = shelter;
		}
	    
	    
	    
	    
	    
	}



