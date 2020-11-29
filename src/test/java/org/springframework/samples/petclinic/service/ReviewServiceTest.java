package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ReviewServiceTest {

	@Autowired
	protected HotelService hotelService;
	@Autowired
	protected HotelService reviewService;
	
	@Test
	@Transactional
	public void shouldFindReviewBylId() {
//		Encuentra review dado su ID
	}
	
	@Test
	@Transactional
	public void shouldFindReviewsByHotelId() {
//		Encuentra listado de reviews de un hotel
	}
	
	
	@Test
	@Transactional
	public void shouldDeleteReviewBylId() {
//		Elimina review dado su ID
	}
	
	@Test
	@Transactional
	public void shouldDeleteReview() {
//		Elimina review
	}
	
	@Test
	@Transactional
	public void shouldDeleteRevieswByHotellId() {
//		Elimina las reviews de un hotel
	}
	
	@Test
	@Transactional
	public void ownerShouldBeAbleToReview() {
//		Comprueba que el owner tiene permiso para dejar reseñas
	}
	
	
	
	
	
}
