package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
class ReviewServiceMockedTests {
	
	@Mock
    private ReviewRepository reviewRepository;
	
	protected ReviewService reviewService;
	
	@BeforeEach
	void setup() {
		reviewService = new ReviewService(reviewRepository);
	}
	
	@Test
	void shouldSaveReview() {
		Review review = new Review();
		review.setStars(5);
		
		Collection<Review> reviews1 = new ArrayList<Review>();
		reviews1.add(review);
		when(this.reviewRepository.findAll()).thenReturn(reviews1);
		
		Collection<Review> reviews2 = (Collection<Review>) this.reviewService.findAll();
		
		assertThat(reviews2).hasSize(1);
	    Review r = reviews2.iterator().next();
	    assertThat(r.getStars()).isEqualTo(5);
	}

}
