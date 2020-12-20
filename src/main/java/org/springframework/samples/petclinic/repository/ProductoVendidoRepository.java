package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.ProductoVendido;

public interface ProductoVendidoRepository extends CrudRepository<ProductoVendido, Integer> {

}
