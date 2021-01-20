package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "animals")
public class Animal extends NamedEntity{
	
	@Column(name = "birth_date")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate birthDate;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private PetType type;

	@ManyToOne
	@JoinColumn(name = "shelter_id")
	private Shelter shelter;
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public PetType getType() {
		return this.type;
	}
	
	public Shelter getShelter() {
		return this.shelter;
	}

	public void setType(PetType type) {
		this.type = type;
	}
	


}
