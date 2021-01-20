package org.springframework.samples.petclinic.repository;

import java.util.List;


import org.springframework.samples.petclinic.model.Animal;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface AnimalRepository extends Repository<Animal, Integer>{
	
	@Query("SELECT ptype FROM PetType ptype ORDER BY ptype.name")
	List<PetType> findPetTypes() throws DataAccessException;
	
	Animal findById(int id) throws DataAccessException;
	
	void save(Animal animal) throws DataAccessException;
}
