package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.model.Shelter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ShelterServiceTest {
	
	@Autowired
	protected ShelterService shelterService;
	
	@Test
	void shouldFindAllShelters() {
		List<Shelter> shelter = (List<Shelter>) this.shelterService.findAll();
		assertThat(shelter.size()==3);
	}
	
	@Test
	void shouldFindShelterWithCorrectId() {
		Shelter shelter = this.shelterService.findById(2);
		assertThat(shelter.getCity().startsWith("Málaga"));
		assertThat(shelter.getAforo()).isEqualTo(4);
	}
	

	
	@Test
	@Transactional
	public void shouldInsertShelterIntoDatabaseAndGenerateId() {
		Collection<Shelter>shelters = (Collection<Shelter>) this.shelterService.findAll();
		int found = shelters.size();
		
		Shelter nuevoShelter = new Shelter();
		nuevoShelter.setId(found+1);
		nuevoShelter.setAforo(50);
		nuevoShelter.setCity("Madrid");
		shelters.add(nuevoShelter);
		
		assertThat(shelters.size()).isEqualTo(found+1);
		this.shelterService.save(nuevoShelter);
		assertThat(nuevoShelter.getId()).isNotNull();
	}
	
	@Transactional
	public void shouldInsertShelterIntoDatabaseAndGenerateIdError() {
		Collection<Shelter>shelters = (Collection<Shelter>) this.shelterService.findAll();
		int found = shelters.size();
		
		Shelter nuevoShelter = new Shelter();
		nuevoShelter.setId(found+1);
		nuevoShelter.setAforo(50);
		nuevoShelter.setCity("");
		shelters.add(nuevoShelter);
		
		assertThrows(ConstraintViolationException.class, () -> {
			nuevoShelter.getCity();
			this.shelterService.save(nuevoShelter);
		});
	}
	
	@Test
	@Transactional
	public void shouldDeleteShelter() {

		Collection<Shelter> shelters = (Collection<Shelter>) this.shelterService.findAll();
		int found = shelters.size();
		Shelter shelter = new Shelter();
		shelter.setId(3);
		shelter.setAforo(4);
		shelter.setCity("Cordoba");
		
		this.shelterService.delete(shelter);
		
		Collection<Shelter> shelters2 = (Collection<Shelter>) this.shelterService.findAll();
	
		assertThat(shelters2.size()).isEqualTo(found -1);		
	}
	
	@Test
	@Transactional
	public void shouldDeleteShelterById() {
		Collection<Shelter> shelter = (Collection<Shelter>) this.shelterService.findAll();
		int found = shelter.size();
	
		this.shelterService.deleteById(1);
		Collection<Shelter> shelters2 = (Collection<Shelter>) this.shelterService.findAll();
		assertThat(shelters2.size()).isEqualTo(found - 1);
	}
}



