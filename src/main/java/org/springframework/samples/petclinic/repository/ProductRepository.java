package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
