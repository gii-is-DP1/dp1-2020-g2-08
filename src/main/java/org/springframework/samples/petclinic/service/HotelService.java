package org.springframework.samples.petclinic.service;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.samples.petclinic.repository.HotelRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
	 @Autowired
	 private HotelRepository hotelRepo;
	 @Autowired
	 private BookingRepository bookingRepo;
	 @Transactional
	 public int hotelCount() {
		 
		 return (int) hotelRepo.count();
	 }
	 @Transactional
	 public Iterable<Hotel> findAll(){
		 return hotelRepo.findAll();
	 }
	 
	 @Transactional
	 public void addBooking(Booking booking) {
		 Hotel hotel = new Hotel();
		  hotel.setAforo(50);
		  hotel.setOcupadas((int) bookingRepo.count());		 
		  hotel.setId(1);
		  hotel.getBookings().add(booking);
	 
//		  hotel.setBookings((Set<Booking>) bookingRepo.findAll());
	 	hotelRepo.save(hotel);

	 }
	 
}
