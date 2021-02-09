package org.springframework.samples.petclinic.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Animal;

import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AdoptionServiceTest {


	@Autowired
	protected AnimalService animalService;
	@Autowired
	protected AdoptionService adoptionService;
	@Autowired
	protected OwnerService ownerService;
	@Autowired
	protected PetService petService;

	@Test
	@Transactional
	public void shouldFindAll() {
		
	}
	@Test
	@Transactional
	public void shouldFindAdoptionById() {
		String animalName = "Sterling";
		String petName = "Sterling";
		Integer ownerId = 1;
		Adoption adoption = this.adoptionService.findAdoptionById(1);
		assertThat(adoption.getAnimal().getName().equals(animalName));
		assertThat(adoption.getPet().getName().equals(petName));
		assertThat(adoption.getOwner().getId().equals(ownerId));
		
	}
	
	@Test
	@Transactional
	public void shouldFindAdoptionByOwnerId() {
		String animalName = "Sterling";
		String petName = "Sterling";
		Integer ownerId = 1;
		List<Adoption> listaAdoption = this.adoptionService.findAdoptionsByOwnerId(1);
		Adoption adoption = listaAdoption.get(0);
		assertThat(adoption.getAnimal().getName().equals(animalName));
		assertThat(adoption.getPet().getName().equals(petName));
		assertThat(adoption.getOwner().getId().equals(ownerId));
	}
	
	@Test
	@Transactional
	public void shouldFindAdoptionByAnimalId() {
		String animalName = "Sterling";
		String petName = "Sterling";
		Integer ownerId = 1;
		List<Adoption> listaAdoption = this.adoptionService.findAdoptionsByAnimalId(1);
		Adoption adoption = listaAdoption.get(0);
		assertThat(adoption.getAnimal().getName().equals(animalName));
		assertThat(adoption.getPet().getName().equals(petName));
		assertThat(adoption.getOwner().getId().equals(ownerId));
	}
	
	@Test
	@Transactional
	public void shouldFindAdoptionByPetId() {
		String animalName = "Sterling";
		String petName = "Sterling";
		Integer ownerId = 1;
		List<Adoption> listaAdoption = this.adoptionService.findAdoptionsByPetId(1);
		Adoption adoption = listaAdoption.get(0);
		assertThat(adoption.getAnimal().getName().equals(animalName));
		assertThat(adoption.getPet().getName().equals(petName));
		assertThat(adoption.getOwner().getId().equals(ownerId));
	}
	
	

	
	@Test
	@Transactional
	public void shouldReturnNumeroAdoptionsPorOwner() {
		Integer countOwnerAdoptions1 = this.adoptionService.numeroAdoptionsPorOwner(1);
		Integer countOwnerAdoptions2 = this.adoptionService.numeroAdoptionsPorOwner(2);
		assertThat(countOwnerAdoptions2).isEqualTo(0);
		assertThat(countOwnerAdoptions1).isEqualTo(1);
	}
	
	
	@Test
	@Transactional
	public void shouldInsertAdoption() {
		List<Adoption>adoptionList =  (List<Adoption>) this.adoptionService.findAll();
		
		int found = adoptionList.size();
		assertThat(found).isEqualTo(1);
		
		
		LocalDate date = LocalDate.now();
		
		Animal animal = this.animalService.findAnimalById(1);
		Adoption adoption = new Adoption();
		Owner owner = this.ownerService.findOwnerById(1);
		Pet pet = this.petService.findPetById(1);
		
		adoption.setAnimal(animal);
		adoption.setdate(date);
		adoption.setOwner(owner);
		adoption.setPet(pet);

		adoptionList.add(adoption);
		assertThat(adoptionList.size()).isEqualTo(found+1);
	}

	@Test
	@Transactional
	public void shouldInsertAdoptionIntoDatabaseAndGenerateId() {
		Collection<Adoption>adoptions = (Collection<Adoption>) this.adoptionService.findAll();
		int found = adoptions.size();
		
		
		LocalDate date = LocalDate.now();
		Animal animal = this.animalService.findAnimalById(1);
		Adoption adoption = new Adoption();
		Owner owner = this.ownerService.findOwnerById(1);
		Pet pet = this.petService.findPetById(1);
		
		adoption.setAnimal(animal);
		adoption.setdate(date);
		adoption.setOwner(owner);
		adoption.setPet(pet);

		adoptions.add(adoption);
		
		assertThat(adoptions.size()).isEqualTo(found+1);
		this.adoptionService.save(adoption);
		assertThat(adoption.getId()).isNotNull();
	}
	
	@Transactional
	public void shouldInsertAdoptionIntoDatabaseAndGenerateIdError() {
		Collection<Adoption>adoptions = (Collection<Adoption>) this.adoptionService.findAll();
		
		LocalDate date = LocalDate.now();
		Animal animal = this.animalService.findAnimalById(1);
		Adoption adoption = new Adoption();
		Owner owner = this.ownerService.findOwnerById(1);
		Pet pet = this.petService.findPetById(1);
		
		adoption.setAnimal(animal);
		adoption.setdate(date);
		adoption.setOwner(owner);
		adoption.setPet(pet);

		adoptions.add(adoption);
		assertThrows(ConstraintViolationException.class, () -> {
			adoption.getAnimal();
			this.adoptionService.save(adoption);
		});
	}
	
}
