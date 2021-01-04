package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.ProductReview;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.samples.petclinic.repository.HotelRepository;
import org.springframework.samples.petclinic.repository.ProductReviewRepository;
import org.springframework.samples.petclinic.repository.ProductoVendidoRepository;
import org.springframework.samples.petclinic.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductReviewService {
	
	@Autowired
	private ClientService clientService;	
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductoVendidoRepository prodVendRepo; 
	@Autowired
	private ProductReviewRepository prodReviewRepo;
	
	 @Transactional
	 public int reviewCount() {
		 
		 return (int) prodReviewRepo.count();
	 }
	 
	 @Transactional
	 public Iterable<ProductReview> findAll(){
		 return prodReviewRepo.findAll();
	 }
	 
	 @Transactional
	 public void save(ProductReview prodReview) {

	 	prodReviewRepo.save(prodReview);
	 	
	 	
	 }
	 @Transactional
	 public Optional<ProductReview> findProductReviewById(int prodReview){
		 return prodReviewRepo.findById(prodReview);
	 }
	 

	 
	 @Transactional
		public void delete(ProductReview prodReview) {
		 prodReviewRepo.delete(prodReview);
			
		}
//	 -------------------------------------------------
	 @Transactional
		public void deleteById(Integer prodReviewId) {
		 prodReviewRepo.deleteById(prodReviewId);
			
		}
	 
	 @Transactional
		public void deleteById(int prodReview) {
			 prodReviewRepo.deleteById(prodReview);
			
		}
//	 --------------------------------------------------
	 

@Transactional
public List<ProductReview> findProductReviewByProductId(Integer productId){
	 List<ProductReview> productReviews = (List<ProductReview>) prodReviewRepo.findAll();
	 	return productReviews.stream().filter(x->x.getProductoVendido().getId().equals(productId)).collect(Collectors.toList());
}
	 



//@Transactional
//public boolean canReview(ProductReview review, Integer clientId) {
//	Client client = review.getClient();
//	List<ProductReview> reviews  = findReviewByHotelId(client.getId());
//	if (reviews.stream().anyMatch(x->x.getOwner().getId().equals(ownerId))) {
//		return false;
//	}
//	
//	else {
//		return true;
//	}
//	
//}

}