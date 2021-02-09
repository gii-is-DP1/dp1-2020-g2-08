package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

	@Entity

	@Table(name = "adoptions")
	public class Adoption extends BaseEntity {
		
		//Fecha de inicio de la adopcion 
	   
	    @Column(name = "date")
		@DateTimeFormat(pattern = "yyyy/MM/dd")
	    private LocalDate date;
	 
		
	    //Animal
	    @OneToOne
		@JoinColumn(name = "animal_id")
		private Animal animal;
	    
	  //Pet
	    @OneToOne
		@JoinColumn(name = "pet_id")
		private Pet pet;
	    
	    //Owner
	    @ManyToOne
	    @JoinColumn(name = "owner_id")
	    private Owner owner;
	   

		public LocalDate getdate() {
			return this.date;
		}

		public void setdate(LocalDate date) {
			this.date = date;
		}

		public Pet getPet() {
			return pet;
		}

		public void setPet(Pet pet) {
			this.pet = pet;
		}

		public Animal getAnimal() {
			return animal;
		}

		public void setAnimal(Animal animal) {
			this.animal = animal;
		}
		
		public Owner getOwner() {
			return owner;
		}

		public void setOwner(Owner owner) {
			this.owner = owner;
		}

	    
	    
	    
	    
	    
	}



