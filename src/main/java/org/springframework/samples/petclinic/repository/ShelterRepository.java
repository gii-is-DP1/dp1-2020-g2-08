package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Shelter;

public interface ShelterRepository extends CrudRepository<Shelter, Integer>{

}
