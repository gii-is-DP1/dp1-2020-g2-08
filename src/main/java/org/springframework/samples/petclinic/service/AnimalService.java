package org.springframework.samples.petclinic.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Animal;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.SexType;
import org.springframework.samples.petclinic.repository.AnimalRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedAnimalNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class AnimalService {

	@Autowired
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
	public Collection<SexType> findSexTypes() throws DataAccessException {
		return animalRepository.findSexTypes();
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
	
	@Transactional(rollbackFor = DuplicatedAnimalNameException.class)
	public void saveAnimal(Animal animal) throws DataAccessException, DuplicatedAnimalNameException {
			Animal otherAnimal=animal.getShelter().getAnimalwithIdDifferent(animal.getName(), animal.getId());
            if (StringUtils.hasLength(animal.getName()) &&  (otherAnimal!= null && otherAnimal.getId()!=animal.getId())) {            	
            	throw new DuplicatedAnimalNameException();
            }else
               animalRepository.save(animal);                
	}
	Comparator comparador = new Comparator(){
	    public int compare(Animal p1, Animal p2) throws ParseException {
	        return (p1.diasEnRefugio()-p2.getDiasEnRefugio());
	    }

		@Override
		public int compare(Object o1, Object o2) {
			// TODO Auto-generated method stub
			return 0;
		}
	};
	
	
	//Saca el animal que mas dias lleva en el refugio (Hay que hacerlo para cada refugio tambien)
	public Animal masTiempoEnRefugio() {
		
		List<Animal> animales = animalRepository.findAllAnimals().stream().filter(x->x.getState().equals("avaible")).collect(Collectors.toList());
	@SuppressWarnings("unchecked")
	Animal res =	Collections.max(animales,comparador);
		
	
		return res;
	}

	public void deleteById(Integer animalId) {
		
		animalRepository.deleteById(animalId);
		
	}
	

}
