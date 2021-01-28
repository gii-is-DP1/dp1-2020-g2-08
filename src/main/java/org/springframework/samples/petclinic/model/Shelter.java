package org.springframework.samples.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sun.istack.NotNull;

import lombok.Data;


@Entity
@Table(name = "shelter")
public class Shelter extends BaseEntity{
	
	@NotNull
	@Column(name = "aforo")
	private Integer aforo;

	

	@NotNull
	@Column(name = "city")
	private String city;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "shelter", fetch = FetchType.LAZY)	
	private Set<Animal> animals;
	
	protected Set<Animal> getAnimalsInternal() {
		if (this.animals == null) {
			this.animals = new HashSet<>();
		}
		return this.animals;
	}
	
	public Animal getAnimalwithIdDifferent(String name, Integer id) {
		name = name.toLowerCase();
		for (Animal animal : getAnimalsInternal()) {
			String compName = animal.getName();
			compName = compName.toLowerCase();
			if (compName.equals(name) && animal.getId()!=id) {
				return animal;
			}
		}
		return null;
	}

	public Integer getAforo() {
		return aforo;
	}

	public void setAforo(Integer aforo) {
		this.aforo = aforo;
	}
 
	 

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Set<Animal> getAnimals() {
		return animals;
	}

	public void setAnimals(Set<Animal> animals) {
		this.animals = animals;
	}

	
	
}
