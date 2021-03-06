package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

}
