package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Owner;

public interface ClientRepository extends CrudRepository<Client,Integer> {

}
