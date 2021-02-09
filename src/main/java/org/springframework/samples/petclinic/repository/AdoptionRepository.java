package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Adoption;

public interface AdoptionRepository extends CrudRepository<Adoption,Integer>{

}
