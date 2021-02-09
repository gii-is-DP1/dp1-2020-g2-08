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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.time.temporal.ChronoUnit;


import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

@Entity
@Table(name = "animals")
public class Animal extends NamedEntity{
	
	@ManyToOne
	@JoinColumn(name = "sex")       
	private SexType sex;
	
	@Column(name = "state")        
	private String state;
	
	@Column(name = "description",  length=1024)        
	private String description;
	
	@Column(name = "image_url",  length=1024)        
	private String imageUrl;
	
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
	
	@OneToOne
	private Adoption adoption;
	
	 @Transient
	 private Integer diasEnRefugio;
	
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
	
	public Adoption getAdoption() {
		return this.adoption;
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

	public Integer getDiasEnRefugio() throws ParseException {
		
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
	
	public SexType getSex() {
		return sex;
	}

	public void setSex(SexType sex) {
		this.sex = sex;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}



	

}
