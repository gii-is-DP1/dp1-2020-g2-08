package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Animal;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.AnimalRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class AnimalService {

	private AnimalRepository animalRepository;

	@Autowired
	public AnimalService(AnimalRepository animalRepository) {
		this.animalRepository = animalRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return animalRepository.findPetTypes();
	}

	@Transactional(readOnly = true)
	public Animal findAnimalById(int id) throws DataAccessException {
		return animalRepository.findById(id);
	}
	
	@Transactional
	public void save(Animal animal) {
		animal.setShelterDate(LocalDate.now());
		 animalRepository.save(animal);
	}
	
	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void saveAnimal(Animal animal) throws DataAccessException, DuplicatedPetNameException {
			Animal otherAnimal=animal.getShelter().getAnimalwithIdDifferent(animal.getName(), animal.getId());
            if (StringUtils.hasLength(animal.getName()) &&  (otherAnimal!= null && otherAnimal.getId()!=animal.getId())) {            	
            	throw new DuplicatedPetNameException();
            }else
               animalRepository.save(animal);                
	}


}
