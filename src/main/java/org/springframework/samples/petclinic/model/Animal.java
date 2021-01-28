package org.springframework.samples.petclinic.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.time.temporal.ChronoUnit;


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
	
	@Column(name = "shelter_date")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate shelterDate;
	
	 @Transient
	 private int diasEnRefugio;
	
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

	

	public LocalDate getShelterDate() {
		return shelterDate;
	}

	public void setShelterDate(LocalDate shelterDate) {
		this.shelterDate = shelterDate;
	}

	public void setShelter(Shelter shelter) {
		this.shelter = shelter;
	}
	
	public Integer diasEnRefugio() {
		return Period.between(this.getShelterDate(), LocalDate.now()).getDays();
	}

	public int getDiasEnRefugio() throws ParseException {
		
		LocalDate inicial = this.shelterDate;
		ZoneId defaultZoneId = ZoneId.systemDefault();
		
		//Hay que crear Date en vez de LocalDate para poder hacer la operacion de obtener los dias entre las 2 fechas
		//(ya que Period.between() lo que acaba devolviendo son los dias sin tener en cuenta mes ni año)
		Date fechaInicial = Date.from(inicial.atStartOfDay(defaultZoneId).toInstant());	
		Date fechaFinal = new Date();
		
		//Con esta operacion obtenemos el numero de dias total
		int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);
		return dias;
	}

	public void setDiasEnRefugio(int diasEnRefugio) {
		this.diasEnRefugio = diasEnRefugio;
	}



	

}
