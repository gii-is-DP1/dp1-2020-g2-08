package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.repository.BookingRepository;

@ExtendWith(MockitoExtension.class)
class BookingServiceMockedTests {
	
	@Mock
    private BookingRepository bookingRepository;
	
	protected BookingService bookingService;
	
	@BeforeEach
	void setup() {
		bookingService = new BookingService(bookingRepository);
	}
	
	@Test
	void shouldInsertBookingPositivo() {
		Booking b = new Booking();
		b.setStartDate(LocalDate.now());            
		
        Collection<Booking> bookings =  new ArrayList<Booking>();
        bookings.add(b);
        when(bookingRepository.findAll()).thenReturn(bookings);
        
        Collection<Booking> bookings1 = (Collection<Booking>) this.bookingService.findAll();
        
        assertThat(bookings1).hasSize(1);
        Booking booking = bookings1.iterator().next();
        assertThat(booking.getStartDate()).isEqualTo(LocalDate.now());
	}
}
