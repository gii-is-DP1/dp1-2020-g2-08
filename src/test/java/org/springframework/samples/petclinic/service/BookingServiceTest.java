package org.springframework.samples.petclinic.service;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BookingServiceTest {

	@Autowired
	protected HotelService hotelService;
	@Autowired
	protected BookingService bookingService;
	
	
	@Test
	@Transactional
	public void shouldFindBookingById() {
		LocalDate in = LocalDate.of(2020, 2, 10);
		LocalDate out = LocalDate.of(2020, 2, 15);
		Booking booking = this.bookingService.findBookingById(1);
		assertThat(booking.getEndDate().isEqual(in));
		assertThat(booking.getEndDate().isEqual(out));
	}
	
	@Test
	@Transactional
	public void shouldFindBookingByOwnerId() {
		LocalDate in = LocalDate.of(2020, 2, 10);
		LocalDate out = LocalDate.of(2020, 2, 15);
		List<Booking> listaReservas = this.bookingService.findBookingsByOwnerId(1);
		Booking booking = listaReservas.get(0);
		assertThat(booking.getEndDate().isEqual(in));
		assertThat(booking.getEndDate().isEqual(out));
	}
	
	@Test
	@Transactional
	public void shouldFindBookingListByOwnerId() {
		Collection<Booking>ownerBookingList = (Collection<Booking>) this.bookingService.findBookingsByOwnerId(1);
//		COMPROBAR QUE 
		
	}
	
	@Test
	@Transactional
	public void shouldFindBookingListByPetId() {
		
	}
	
	@Test
	@Transactional
	public void shouldFindBookingByHotelId() {
		
	}
	
	@Test
	@Transactional
	public void shouldDeleteByBookingId() {
		
	}
	
	@Test
	@Transactional
	public void shouldDeleteByBookig() {
		
	}
	
	@Test
	@Transactional
	public void shouldInsertBooking() {
		
	}
	
	@Test
	@Transactional
	public void shouldValidateBookingDays() {
		
	}
	
	@Test
	@Transactional
	public void shouldValidateBookingDate() {
		
	}
	
	
}
