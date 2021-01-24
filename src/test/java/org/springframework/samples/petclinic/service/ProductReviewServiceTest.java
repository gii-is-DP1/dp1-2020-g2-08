package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.ProductReview;
import org.springframework.samples.petclinic.repository.ProductReviewRepository;
import org.springframework.samples.petclinic.repository.ProductoVendidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ProductReviewServiceTest {

	@Autowired
	protected ProductReviewService productReviewService;
	@Autowired
	protected ClientService clientService;
	@Autowired
	protected ProductService productService;
	@Autowired
	protected ProductoVendidoRepository productoVendidoRepository;
	@Autowired
	protected ProductReviewRepository productoReviewRepository;
	
	@Test
	@Transactional
	public void shouldSaveProductReview() {
		List<ProductReview> productReviews = (List<ProductReview>) productReviewService.findAll();		
		int tam = productReviews.size();
		
		ProductReview productReview = new ProductReview();
		productReview.setClient(clientService.findById(1));
		productReview.setId(10);
		productReview.setProductoVendido(productoVendidoRepository.findById(1).get());
		productReview.setStars(5);
		
		productReviewService.save(productReview);		
		
		List<ProductReview> productReviews2 = (List<ProductReview>) productReviewService.findAll();
		
		assertThat(productReviews2.size()).isEqualTo(tam+1);
	}
	
	@Test
	@Transactional
	public void shouldDeleteProductReview() {
		
		List<ProductReview> productReviews= (List<ProductReview>) productReviewService.findAll();
		
		ProductReview productReview =  (ProductReview) productReviewService.findProductReviewById(1).get();
		productReviewService.delete(productReview);
		
		List<ProductReview> productReviews2= (List<ProductReview>) productReviewService.findAll();
		
		assertThat(productReviews2.size()== productReviews.size()-1);
	}
	
	@Test
	@Transactional
	public void shouldDeleteRevieswByProductId() {
		List<ProductReview> productReviews= (List<ProductReview>) productReviewService.findAll();
		
		productReviewService.deleteById(1);
		
		List<ProductReview> productReviews2= (List<ProductReview>) productReviewService.findAll();
		
		assertThat(productReviews2.size()== productReviews.size()-1);
	}
	
	@Test
	@Transactional
	public void average() {
		Product product = this.productService.findProductById(1).get();
		
		assertNotNull(this.productReviewService.average(product));
		
	}
}
