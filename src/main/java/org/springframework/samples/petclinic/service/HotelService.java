package org.springframework.samples.petclinic.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.repository.HotelRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
	 
	 @Autowired
	 private HotelRepository hotelRepo;
	 @Autowired
	 private ReviewService reviewService;
	 @Autowired
	 private BookingService bookingService;
	 
	 @Transactional
	 public int hotelCount() {
		 return (int) hotelRepo.count();
	 }
	 
	 @Transactional
	 public Iterable<Hotel> findAll(){
		 return hotelRepo.findAll();
	 }
	 
	 @Transactional
	 public Hotel findById(Integer hotelId){
		 return hotelRepo.findById(hotelId).get();
	 }
	 
	 @Transactional
	 public List<Review> getReviewsByHotelId(Integer hotelId){
		 return reviewService.findReviewByHotelId(hotelId);}	
	 
	 @Transactional
	 public boolean tieneReviews(Integer hotelId){
		 boolean res =(hotelRepo.findById(hotelId).get().getReviews().size())>0;
		 return res;
	 }	
	 
	 @Transactional
	 public boolean tieneBookings(Integer hotelId){
		 boolean res =(hotelRepo.findById(hotelId).get().getBookings().size())>0;
		 return res;
	 }	

	 @Transactional
	 public void save(Hotel hotel) {
		 hotelRepo.save(hotel);
	 }
	
	 @Transactional
	 public void delete(Hotel hotel) {	
		 hotelRepo.delete(hotel);	
	 }
	
	 public void deleteById(Integer hotelId) {
		 bookingService.eliminarBookingsPorHotel(hotelId);
		 reviewService.eliminarReviewsPorHotel(hotelId);
		 hotelRepo.deleteById(hotelId);
	 }
}
