package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Animal;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.SexType;

public interface AnimalRepository extends Repository<Animal, Integer>{
	
	@Query("SELECT ptype FROM PetType ptype ORDER BY ptype.name")
	List<PetType> findPetTypes() throws DataAccessException;
	
	Animal findById(int id) throws DataAccessException;
	
	void save(Animal animal) throws DataAccessException;
	
	@Query("SELECT animal FROM Animal animal ")
	List<Animal> findAllAnimals() throws DataAccessException;

	void deleteById(Integer animalId);

	@Query("SELECT psex FROM SexType psex ORDER BY psex.name")
	List<SexType> findSexTypes() throws DataAccessException;
}
