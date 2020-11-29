package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class HotelServiceTest {

	@Autowired
	protected HotelService hotelService;
	@Autowired
	protected ReviewService reviewService;
	@Autowired
	protected BookingService BookingService;
	
	@Test
	void shouldFindHotelWithCorrectId() {
//		TODO
		Hotel hotel = this.hotelService.findById(2);
		assertThat(hotel.getCity().startsWith("Cordoba"));
		assertThat(hotel.getAforo()).isEqualTo(50);
	}
	
	@Test
	@Transactional
	public void shouldInsertHotelIntoDatabaseAndGenerateId() {
//		TODO
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
	public void shouldDeleteHotelById() {
//		TODO
		Collection<Hotel> hotels = (Collection<Hotel>) this.hotelService.findAll();
		int found = hotels.size();
		System.out.println(">>>>>> HOTELES ANTES DE BORRAR: "+hotels.size() );
		Hotel hotel = new Hotel();
		hotel.setId(10);
		hotel.setAforo(50);
		hotel.setCity("Cordoba");
		hotel.setOcupadas(0);
		this.hotelService.deleteById(1);
		Collection<Hotel> hotels2 = (Collection<Hotel>) this.hotelService.findAll();
		System.out.println(">>>>>> HOTELES DESPUES DE BORRAR: "+hotels2.size() );
		assertThat(hotels2.size()).isEqualTo(found - 1);
		assertThat(hotel.getId()).isNotNull();
		
	}
	
	@Test
	@Transactional
	public void shouldDeleteHotel() {
//		TODO
		Collection<Hotel> hotels = (Collection<Hotel>) this.hotelService.findAll();
		int found = hotels.size();
		System.out.println(">>>>>> HOTELES ANTES DE BORRAR: "+hotels.size() );
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setAforo(50);
		hotel.setCity("Sevilla");
		hotel.setOcupadas(0);

		hotels.remove(hotel);
		System.out.println(">>>>>> HOTELES DESPUES DE BORRAR: "+hotels.size() );
		assertThat(hotels.size()).isEqualTo(found - 1);
		this.hotelService.delete(hotel);
		
		assertThat(hotel.getId()).isNotNull();
	}
	
	@Test
	@Transactional
	public void containsReview() {
//		TODO

	}
	
	@Test
	@Transactional
	public void containsBookins() {
//		TODO
	}
	
	
	
	
	
}

