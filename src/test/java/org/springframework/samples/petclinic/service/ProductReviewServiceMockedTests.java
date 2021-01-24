package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.ProductReview;
import org.springframework.samples.petclinic.repository.ProductReviewRepository;

@ExtendWith(MockitoExtension.class)
class ProductReviewServiceMockedTests {
	
	@Mock
    private ProductReviewRepository productReviewRepository;
	
	protected ProductReviewService productReviewService;
	
	@BeforeEach
	void setup() {
		productReviewService = new ProductReviewService(productReviewRepository);
	}
	
	@Test
	void shouldSaveProductReview() {
		ProductReview productReview = new ProductReview();
		productReview.setStars(5);
		
		Collection<ProductReview> reviews1 = new ArrayList<ProductReview>();
		reviews1.add(productReview);
		when(this.productReviewRepository.findAll()).thenReturn(reviews1);
		
		Collection<ProductReview> reviews2 = (Collection<ProductReview>) this.productReviewService.findAll();
		
		assertThat(reviews2).hasSize(1);
	    ProductReview pr = reviews2.iterator().next();
	    assertThat(pr.getStars()).isEqualTo(5);
	}
	
	@Test
	void shouldDeleteProductReview() {
		ProductReview productReview = new ProductReview();
		productReview.setStars(5);
		
		Collection<ProductReview> reviews1 = new ArrayList<ProductReview>();
		reviews1.add(productReview);
		when(productReviewRepository.findAll()).thenReturn(reviews1);
		
		Collection<ProductReview> reviews2 = (Collection<ProductReview>) this.productReviewService.findAll();
		reviews2.remove(productReview);
		
		assertThat(reviews2).hasSize(0);
	}
}
