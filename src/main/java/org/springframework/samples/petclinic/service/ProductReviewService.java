package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.ProductReview;
import org.springframework.samples.petclinic.repository.ProductReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductReviewService {
	
	@Autowired
	private ProductReviewRepository prodReviewRepo;
	
	@Autowired
	public ProductReviewService(ProductReviewRepository productReviewRepository) {
		this.prodReviewRepo = productReviewRepository;
	}

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
	public List<Integer> findProductReviewByProductName(String name){
		List<ProductReview> productReviews = (List<ProductReview>) prodReviewRepo.findAll();
	 	return productReviews.stream().filter(x->x.getProductoVendido().getNombre().startsWith(name)).map(x->x.getStars()).collect(Collectors.toList());
	}

	public Double average(Product product) {
		if(product.getReviews().size()==0) {
			return 0.0;
		}else {
			Double average = product.getReviews().stream().mapToDouble(x->x.getStars()).average().getAsDouble();
			return (double) Math.round(average);
		}
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
