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
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.repository.HotelRepository;

@ExtendWith(MockitoExtension.class)
class HotelServiceMockedTests {
	
	@Mock
    private HotelRepository hotelRepository;
	
	protected HotelService hotelService;
	
	@BeforeEach
	void setup() {
		hotelService = new HotelService(hotelRepository);
	}
	
	@Test
	void shouldInsertHotelPositivo() {
		Hotel h = new Hotel();
		h.setCity("Malaga");    
		h.setAforo(5);
		
        Collection<Hotel> hotels =  new ArrayList<Hotel>();
        hotels.add(h);
        when(hotelRepository.findAll()).thenReturn(hotels);
        
        Collection<Hotel> hotels1 = (Collection<Hotel>) this.hotelService.findAll();
        
        assertThat(hotels1).hasSize(1);
        Hotel hotel = hotels1.iterator().next();
        assertThat(hotel.getCity()).isEqualTo("Malaga");
        assertThat(hotel.getAforo()).isEqualTo(5);
	}
}
