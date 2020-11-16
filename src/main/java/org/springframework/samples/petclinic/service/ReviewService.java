package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
public class ReviewService {
	@Autowired
	HotelService hotelService;
	
	 @Autowired
	 private HotelRepository hotelRepo;
	 @Autowired
	 private BookingRepository bookingRepo;
	 
	 @Autowired
	 private ReviewRepository reviewRepo;
	 
	 @Transactional
	 public int reviewCount() {
		 
		 return (int) reviewRepo.count();
	 }
	 
	 @Transactional
	 public Iterable<Review> findAll(){
		 return reviewRepo.findAll();
	 }
	 
	 @Transactional
	 public Optional<Review> findReviewById(int review){
		 return reviewRepo.findById(review);
	 }
	 @Transactional
	public void deleteById(int review) {
		 reviewRepo.deleteById(review);
		
	}
	 
	 @Transactional
		public void delete(Review review) {
		 reviewRepo.delete(review);
			
		}
	 
	 @Transactional
		public void deleteById(Integer reviewId) {
		 reviewRepo.deleteById(reviewId);
			
		}
@Transactional
public void save(Review review) {

	reviewRepo.save(review);
	
	
}

@Transactional
public List<Review> findReviewByHotelId(Integer hotelId){
	 List<Review> reviews = (List<Review>) reviewRepo.findAll();
	 	return reviews.stream().filter(x->x.getHotel().getId().equals(hotelId)).collect(Collectors.toList());
}
	 

}
