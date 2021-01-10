//package org.springframework.samples.petclinic.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.samples.petclinic.model.ProductReview;
//import org.springframework.samples.petclinic.model.Review;
//import org.springframework.samples.petclinic.repository.ProductoVendidoRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
//public class ProductReviewServiceTest {
//
//	@Autowired
//	protected ProductReviewService productReviewService;
//	@Autowired
//	protected ClientService clientService;
//	@Autowired
//	protected ProductoVendidoRepository productoVendidoRepository;
//	
//	@Test
//	@Transactional
//	public void shouldSaveProductReview() {
////		Guarda una productReview
//		List<ProductReview> productReviews = (List<ProductReview>) productReviewService.findAll();		
//		System.out.println("--------------------------------------Numero de productReviews al inicio: " + productReviews.size());
//		int tam = productReviews.size();
//		ProductReview productReview = new ProductReview();
//		productReview.setClient(clientService.findById(1));
//		productReview.setId(10);
//		productReview.setProductoVendido(productoVendidoRepository.findById(1).get());
//		productReview.setStars(5);
//		
//		productReviewService.save(productReview);		
//		
//		List<ProductReview> productReviews2 = (List<ProductReview>) productReviewService.findAll();
//		
//		System.out.println("------------------------------------------Numero de productReviews tras guardar una: "+ productReviews2.size());
//		assertThat(productReviews.size()).isEqualTo(tam);
//		assertThat(productReviews2.size()).isEqualTo(tam+1);
//	}
//	
//	@Test
//	@Transactional
//	public void shouldDeleteProductReview() {
////		Elimina productReview
//		
//		List<ProductReview> productReviews= (List<ProductReview>) productReviewService.findAll();
//		System.out.println("------------------------------------------------------>>>>>> ProductReviews antes de borrar: "+ productReviews.size() );
//		
//		ProductReview productReview =  (ProductReview) productReviewService.findProductReviewByProductId(1).get(0);
//		productReviewService.delete(productReview);
//		
//		List<ProductReview> productReviews2= (List<ProductReview>) productReviewService.findAll();
//		System.out.println("------------------------------------------------------>>>>>> ProductReviews despues de borrar: "+ productReviews2.size());
//		
//		assertThat(productReviews2.size()== productReviews.size()-1);
//		
//	}
//	
//	@Test
//	@Transactional
//	public void shouldDeleteRevieswByProductId() {
////		Elimina las reviews de un product
//		List<ProductReview> productReviews= (List<ProductReview>) productReviewService.findProductReviewByProductId(1);
//		System.out.println("------------------------------------------------------>>>>>> productReviews del product 1 ANTES DE BORRAR: "+ productReviews.size() );
//		
//		productReviewService.deleteById(1);
//		
//		
//		List<ProductReview> productReviews2= (List<ProductReview>) productReviewService.findProductReviewByProductId(1);
//		System.out.println("------------------------------------------------------>>>>>> productReviews del product 1 DESPUES DE BORRAR: "+ productReviews2.size());
//		
//		assertThat(productReviews2.size()== productReviews.size()-1);
//		
//		
//	}
//}
