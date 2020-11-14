package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.samples.petclinic.repository.HotelRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
	
	
	 @Autowired
	 private BookingRepository bookingRepo;
	 
	 @Autowired
	 private HotelRepository hotelRepo;
	 
	 @Transactional
	 public int bookingCount() {
		 
		 return (int) bookingRepo.count();
	 }
	 @Transactional
	 public Iterable<Booking> findAll(){
		 return bookingRepo.findAll();
	 }
	 
	 @Transactional
	 public Booking findBookingById(int bookingId){
		 return bookingRepo.findById(bookingId).get();
	 }
	 
	 @Transactional
	 public List<Booking> findBookingsByOwnerId(int ownerId){
		 List<Booking> lista =new ArrayList<Booking>();
		 lista=(List<Booking>) bookingRepo.findAll();
		return lista.stream().filter(x->x.getOwner().getId().equals(ownerId)).collect(Collectors.toList());
	 }
	 
	 @Transactional
	public void deleteById(int bookingId) {
		bookingRepo.deleteById(bookingId);
		
	}
	 
	 @Transactional
		public void delete(Booking booking) {
			bookingRepo.delete(booking);
			
		}
@Transactional
public void save(Booking booking) {
	bookingRepo.save(booking);
	
	
}

}
