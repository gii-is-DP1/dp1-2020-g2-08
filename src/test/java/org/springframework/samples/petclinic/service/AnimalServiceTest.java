package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;
import org.springframework.samples.petclinic.model.Animal;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.SexType;
import org.springframework.samples.petclinic.model.Shelter;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedAnimalNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AnimalServiceTest {
	
	  @Autowired
		protected AnimalService animalService;
	  
	  @Autowired
	  protected ShelterService shelterService;
	        	

		@Test
		void shouldFindAnimalWithCorrectId() {
			Animal animal1 = this.animalService.findAnimalById(1);
			assertThat(animal1.getName()).startsWith("Sterling");

		}

		@Test
		void shouldFindAllPetTypes() {
			Collection<PetType> petTypes = this.animalService.findPetTypes();

			PetType petType1 = EntityUtils.getById(petTypes, PetType.class, 1);
			assertThat(petType1.getName()).isEqualTo("cat");
			PetType petType4 = EntityUtils.getById(petTypes, PetType.class, 4);
			assertThat(petType4.getName()).isEqualTo("snake");
		}

		@Test
		@Transactional
		public void shouldInsertAnimalIntoDatabaseAndGenerateId() {
			Shelter shelter2 = this.shelterService.findById(2);
			int found = shelter2.getAnimals().size();
			Collection<PetType> types = this.animalService.findPetTypes();
			Collection<SexType> sextypes = this.animalService.findSexTypes();

			Animal animal = new Animal();
			animal.setName("bowser");
			animal.setType(EntityUtils.getById(types, PetType.class, 2));
			animal.setBirthDate(LocalDate.now());
			shelter2.addAnimal(animal);
			animal.setShelter(shelter2);
			animal.setShelterDate(LocalDate.now());
			animal.setSex(EntityUtils.getById(sextypes, SexType.class, 1));
			assertThat(shelter2.getAnimals().size()).isEqualTo(found + 1);

	            try {
	                this.animalService.saveAnimal(animal);
	            } catch (DuplicatedAnimalNameException ex) {
	                Logger.getLogger(AnimalServiceTest.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        this.shelterService.save(shelter2);
	        shelter2 = this.shelterService.findById(2);
	        assertThat(shelter2.getAnimals().size()).isEqualTo(found + 1);
			assertThat(animal.getId()).isNotNull();
		}
		
		@Test
		@Transactional
		public void shouldThrowExceptionInsertingAnimalsWithTheSameName() {
			Collection<PetType> types = this.animalService.findPetTypes();
			Collection<SexType> sextypes = this.animalService.findSexTypes();
			Shelter shelter2 = this.shelterService.findById(2);
			
			Animal animal = new Animal();
			animal.setName("wario");
			animal.setType(EntityUtils.getById(types, PetType.class, 2));
			animal.setBirthDate(LocalDate.now());
			animal.setShelter(shelter2);
			animal.setShelterDate(LocalDate.now());
			animal.setSex(EntityUtils.getById(sextypes, SexType.class, 1));
			shelter2.addAnimal(animal);
			try {
				animalService.saveAnimal(animal);		
			} catch (DuplicatedAnimalNameException e) {
				// The pet already exists!
				e.printStackTrace();
			}
			
			Animal anotherAnimalWithTheSameName = new Animal();		
			anotherAnimalWithTheSameName.setName("wario");
			anotherAnimalWithTheSameName.setType(EntityUtils.getById(types, PetType.class, 1));
			anotherAnimalWithTheSameName.setBirthDate(LocalDate.now().minusWeeks(2));
			anotherAnimalWithTheSameName.setShelter(shelter2);
			anotherAnimalWithTheSameName.setShelterDate(LocalDate.now());
			anotherAnimalWithTheSameName.setSex(EntityUtils.getById(sextypes, SexType.class, 1));
			Assertions.assertThrows(DuplicatedAnimalNameException.class, () ->{
				shelter2.addAnimal(anotherAnimalWithTheSameName);
				animalService.saveAnimal(anotherAnimalWithTheSameName);
			});		
		}

		@Test
		@Transactional
		public void shouldUpdateAnimalName() throws Exception {
			Animal animal3 = this.animalService.findAnimalById(1);
			String oldName = animal3.getName();

			String newName = oldName + "X";
			animal3.setName(newName);
			this.animalService.saveAnimal(animal3);

			animal3 = this.animalService.findAnimalById(1);
			assertThat(animal3.getName()).isEqualTo(newName);
		}
		
		@Test
		@Transactional
		public void shouldThrowExceptionUpdatingAnimalsWithTheSameName() {
			Shelter shelter2 = this.shelterService.findById(1);
			Collection<PetType> types = this.animalService.findPetTypes();
			Collection<SexType> sextypes = this.animalService.findSexTypes();
			
			Animal animal = new Animal();
			animal.setName("wario");
			animal.setType(EntityUtils.getById(types, PetType.class, 1));
			animal.setBirthDate(LocalDate.now());
			animal.setShelter(shelter2);
			animal.setShelterDate(LocalDate.now());
			animal.setSex(EntityUtils.getById(sextypes, SexType.class, 1));
			shelter2.addAnimal(animal);
			
			Animal anotherAnimal = new Animal();		
			anotherAnimal.setName("waluigi");
			anotherAnimal.setType(EntityUtils.getById(types, PetType.class, 3));
			anotherAnimal.setBirthDate(LocalDate.now().minusWeeks(2));
			anotherAnimal.setShelter(shelter2);
			anotherAnimal.setShelterDate(LocalDate.now());
			anotherAnimal.setSex(EntityUtils.getById(sextypes, SexType.class, 2));
			shelter2.addAnimal(anotherAnimal);
			
			try {
				animalService.saveAnimal(animal);
				animalService.saveAnimal(anotherAnimal);
			} catch (DuplicatedAnimalNameException e) {
				// The pets already exists!
				e.printStackTrace();
			}				
				
			Assertions.assertThrows(DuplicatedAnimalNameException.class, () ->{
				anotherAnimal.setName("wario");
				animalService.saveAnimal(anotherAnimal);
			});		
		}


}
