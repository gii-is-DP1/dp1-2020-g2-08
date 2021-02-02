package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ReviewServiceTest {

	@Autowired
	protected HotelService hotelService;
	@Autowired
	protected ReviewService reviewService;
	@Autowired
	protected OwnerService ownerService;
	
	@Test
	@Transactional
	public void shouldSaveReview() {
		//Guarda una review
		
		List<Review> reviews = (List<Review>)reviewService.findAll();		
		System.out.println("--------------------------------------Numero de reviews al inicio: "+reviews.size());
		int tam = reviews.size();
		Review review = new Review();
		review.setDescription("Prueba JUNIT");
		review.setHotel(hotelService.findById(1));
		review.setId(10);
		review.setReviewDate(LocalDate.now());
		review.setStars(5);
		review.setTittle("Prueba 1");
		review.setOwner(ownerService.findOwnerById(1));
		
		reviewService.save(review);		
		
		List<Review> reviews2 = (List<Review>)reviewService.findAll();
		
		System.out.println("------------------------------------------Numero de reviews tras guardar una: "+reviews2.size());
		assertThat(reviews.size()).isEqualTo(tam);
		assertThat(reviews2.size()).isEqualTo(tam+1);
	}
	
	@Test
	@Transactional
	public void shouldFindReviewBylId() {
		Review review1=	this.reviewService.findReviewById(1).get();
		Review review2=	this.reviewService.findReviewById(5).orElse(null);	
		
		assertThat(review1).isNotNull();
		assertThat(review2).isNull();
	}
	
	@Test
	@Transactional
	public void shouldDeleteReview() {
		//Elimina review
		
		List<Review> reviews= (List<Review>) reviewService.findAll();
		System.out.println("------------------------------------------------------>>>>>> REVIEWS ANTES DE BORRAR: "+reviews.size() );
		
		Review review =  reviewService.findReviewByHotelId(1).get(0);
		reviewService.delete(review);
		
		List<Review> reviews2= (List<Review>) reviewService.findAll();
		System.out.println("------------------------------------------------------>>>>>> REVIEWS DESPUES DE BORRAR: "+reviews2.size());
		
		assertThat(reviews2.size()== reviews.size()-1);
	}
	
	@Test
	@Transactional
	public void shouldDeleteReviewBylId() {
		//Elimina review dado su ID
		
		List<Review> reviews= (List<Review>) reviewService.findAll();
		System.out.println("------------------------------------------------------>>>>>> REVIEWS ANTES DE BORRAR: "+reviews.size() );
		
		reviewService.deleteById(1);
		
		List<Review> reviews2= (List<Review>) reviewService.findAll();
		System.out.println("------------------------------------------------------>>>>>> REVIEWS DESPUES DE BORRAR: "+reviews2.size());
		
		assertThat(reviews2.size()== reviews.size()-1);
	}
	
	@Test
	@Transactional
	public void shouldFindReviewsByHotelId() {
		//Encuentra listado de reviews de un hotel
		
		int res1=this.reviewService.findReviewByHotelId(1).size();
		int res2=this.reviewService.findReviewByHotelId(2).size();
		int res3=this.reviewService.findReviewByHotelId(3).size();
		int res4=this.reviewService.findReviewByHotelId(4).size();
		
		assertThat(res1==1);
		assertThat(res2==1);
		assertThat(res3==1);
		assertThat(res4==0);
	}
	
	@Test
	@Transactional
	public void shouldDeleteRevieswByHotellId() {
		//Elimina las reviews de un hotel
		
		List<Review> reviews= (List<Review>) reviewService.findReviewByHotelId(1);
		System.out.println("------------------------------------------------------>>>>>> REVIEWS en el hotel 1 ANTES DE BORRAR: "+reviews.size() );
		
		reviewService.eliminarReviewsPorHotel(1);
		
		List<Review> reviews2= (List<Review>) reviewService.findReviewByHotelId(1);
		System.out.println("------------------------------------------------------>>>>>> REVIEWS del hotel 1 DESPUES DE BORRAR: "+reviews2.size());
		
		assertThat(reviews2.size()== reviews.size()-1);
	}
	
	@Test
	@Transactional
	public void ownerShouldBeAbleToReview() {
		//Comprueba que el owner tiene permiso para dejar reseñas
		
		Review review = new Review();
		review.setDescription("Prueba");
		review.setHotel(hotelService.findById(1));
		review.setId(99);
		review.setReviewDate(LocalDate.now());
		review.setStars(5);
		review.setTittle("prueba 1");
		review.setOwner(ownerService.findOwnerById(12));
		
		assertThat(reviewService.puedeReseñar(review , 12)==false);
		assertThat(reviewService.puedeReseñar(review , 13)==true);
		assertThat(reviewService.puedeReseñar(review , 1)==true);
	}
}
