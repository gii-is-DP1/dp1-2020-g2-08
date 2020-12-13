package org.springframework.samples.petclinic.service;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import static java.time.temporal.ChronoUnit.DAYS;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BookingServiceTest {

	@Autowired
	protected HotelService hotelService;
	@Autowired
	protected BookingService bookingService;
	@Autowired
	protected OwnerService ownerService;
	@Autowired
	protected PetService petService;

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
		Collection<Booking>ownerBookingList1 = (Collection<Booking>) this.bookingService.findBookingsByOwnerId(5);
		Collection<Booking>ownerBookingList2 = (Collection<Booking>) this.bookingService.findBookingsByOwnerId(2);
		assertThat(ownerBookingList1.isEmpty());
		assertThat(ownerBookingList2).isNotNull();
		
	}
	
	@Test
	@Transactional
	public void shouldReturnNumeroBookingsPorOwner() {
		Integer countOwnerBookings1 = this.bookingService.numeroBookingsPorOwner(1);
		Integer countOwnerBookings2 = this.bookingService.numeroBookingsPorOwner(5);
		assertThat(countOwnerBookings1).isNotNull();
		assertThat(countOwnerBookings2).isEqualTo(0);
	}
	
	@Test
	@Transactional
	public void shouldReturnNumeroBookingsPorPet() {
		Integer countPetBookings1 = this.bookingService.numeroBookingsPorPet(1);
		Integer countPetBookings2 = this.bookingService.numeroBookingsPorPet(5);
		assertThat(countPetBookings1).isNotNull();
		assertThat(countPetBookings2).isEqualTo(0);
	}
	
	@Test
	@Transactional
	public void shouldFindBookingByHotelId() {
		List<Booking>hotelBookingList1 = this.bookingService.findBookingsByHotelId(1);
		List<Booking>hotelBookingList2 = this.bookingService.findBookingsByHotelId(2);
		assertThat(hotelBookingList1).isNotNull();
		assertThat(hotelBookingList2).isEmpty();
	}
	
	@Test
	@Transactional
	public void shouldDeleteByBookingId() {
//		TODO
		List<Booking> bookings= (List<Booking>) bookingService.findAll();
		System.out.println("------------------------------------------------------>>>>>> BOOKINGS ANTES DE BORRAR: "+bookings.size() );
		
		
		bookingService.deleteById(1);
		
		List<Booking> bookings2= (List<Booking>) bookingService.findAll();
		System.out.println("------------------------------------------------------>>>>>> BOOKINGS despues DE BORRAR: "+bookings2.size() );
		
		assertThat(bookings2.size()== bookings.size()-1);
		
		
	}
	
	@Test
	@Transactional
	public void shouldDeleteByBookig() {
		List<Booking> bookings= (List<Booking>) bookingService.findAll();
		System.out.println("------------------------------------------------------>>>>>> BOOKINGS ANTES DE BORRAR: "+bookings.size() );
		
		
		Booking booking = bookingService.findBookingById(1);
		bookingService.delete(booking);
		
		List<Booking> bookings2= (List<Booking>) bookingService.findAll();
		System.out.println("------------------------------------------------------>>>>>> BOOKINGS despues DE BORRAR: "+bookings2.size() );
		
		assertThat(bookings2.size()== bookings.size()-1);
	}
	
	@Test
	@Transactional
	public void shouldInsertBooking() {
		List<Booking>bookingList =  (List<Booking>) this.bookingService.findAll();
		
		
		int found = bookingList.size();
		assertThat(found).isEqualTo(3);
		
		
		LocalDate in = LocalDate.now();
		LocalDate out = LocalDate.of(2020, 12, 3);
		
		Hotel hotel = this.hotelService.findById(1);
		Booking booking = new Booking();
		Owner owner = this.ownerService.findOwnerById(1);
		Pet pet = this.petService.findPetById(1);
		
		booking.setEndDate(out);
		booking.setStartDate(in);
		booking.setHotel(hotel);
		booking.setOwner(owner);
		booking.setPet(pet);

		bookingList.add(booking);
		assertThat(bookingList.size()).isEqualTo(found+1);
	}
	
	@Test
	@Transactional
	public void shouldValidateBookingDays() {
		Booking booking = this.bookingService.findBookingById(1);
		long days = DAYS.between(booking.getStartDate(), booking.getEndDate());
		assertThat(days).isGreaterThan(0);
		assertThat(days).isLessThan(8);
	}
	
	@Test
	@Transactional
	public void shouldValidateBookingDate() {
		Booking booking = this.bookingService.findBookingById(1);
		LocalDate in = booking.getStartDate();
		LocalDate out = booking.getEndDate();
		assertThat(in.isBefore(out));
	}
	
	
}
