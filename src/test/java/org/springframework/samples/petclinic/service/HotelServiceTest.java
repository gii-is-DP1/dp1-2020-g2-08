package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class HotelServiceTest {
	
	@Autowired
	protected HotelService hotelService;
	@Autowired
	protected ReviewService reviewService;
	@Autowired
	protected BookingService bookingService;
	
	@Test
	void shouldFindAllHotels() {
		List<Hotel> hotel = (List<Hotel>) this.hotelService.findAll();
		assertThat(hotel.size()==3);
	}
	
	@Test
	void shouldFindHotelWithCorrectId() {
		Hotel hotel = this.hotelService.findById(2);
		assertThat(hotel.getCity().startsWith("Cordoba"));
		assertThat(hotel.getAforo()).isEqualTo(5);
	}
	
	@Test
	@Transactional
	public void containsReview() {
		List<Review> reviews = reviewService.findReviewByHotelId(1);
		assertThat(reviews.size()>0);
	}
	
	@Test
	@Transactional
	public void containsBookins() {
		List<Booking> bookings= bookingService.findBookingsByHotelId(1);
		assertThat(bookings.size()>0);
	}
	
	@Test
	@Transactional
	public void shouldInsertHotelIntoDatabaseAndGenerateId() {
		Collection<Hotel>hotels = (Collection<Hotel>) this.hotelService.findAll();
		int found = hotels.size();
		
		Hotel nuevoHotel = new Hotel();
		nuevoHotel.setId(found+1);
		nuevoHotel.setAforo(50);
		nuevoHotel.setCity("Madrid");
		nuevoHotel.setOcupadas(5);
		hotels.add(nuevoHotel);
		
		assertThat(hotels.size()).isEqualTo(found+1);
		this.hotelService.save(nuevoHotel);
		assertThat(nuevoHotel.getId()).isNotNull();
	}
	
	@Test
	@Transactional
	public void shouldDeleteHotel() {

		Collection<Hotel> hotels = (Collection<Hotel>) this.hotelService.findAll();
		int found = hotels.size();
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setAforo(50);
		hotel.setCity("Sevilla");
		hotel.setOcupadas(0);
		
		bookingService.eliminarBookingsPorHotel(hotel.getId());
		reviewService.eliminarReviewsPorHotel(hotel.getId());
		this.hotelService.delete(hotel);
		
		Collection<Hotel> hotels2 = (Collection<Hotel>) this.hotelService.findAll();
	
		assertThat(hotels2.size()).isEqualTo(found -1);		
	}
	
	@Test
	@Transactional
	public void shouldDeleteHotelById() {
		Collection<Hotel> hotels = (Collection<Hotel>) this.hotelService.findAll();
		int found = hotels.size();
		
		bookingService.eliminarBookingsPorHotel(1);
		reviewService.eliminarReviewsPorHotel(1);
		this.hotelService.deleteById(1);
		Collection<Hotel> hotels2 = (Collection<Hotel>) this.hotelService.findAll();
		assertThat(hotels2.size()).isEqualTo(found - 1);
	}
}
