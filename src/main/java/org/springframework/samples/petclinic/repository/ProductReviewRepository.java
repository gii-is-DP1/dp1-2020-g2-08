package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.ProductReview;

public interface ProductReviewRepository extends CrudRepository<ProductReview, Integer> {

}
