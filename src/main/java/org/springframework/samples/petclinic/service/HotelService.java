package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.samples.petclinic.repository.HotelRepository;
import org.springframework.samples.petclinic.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
	 @Autowired
	 private HotelRepository hotelRepo;
	 @Autowired
	 private BookingRepository bookingRepo;
	 @Autowired
	 private ReviewRepository reviewRepo;
	 
	 @Autowired
	 private ReviewService reviewService;
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
}