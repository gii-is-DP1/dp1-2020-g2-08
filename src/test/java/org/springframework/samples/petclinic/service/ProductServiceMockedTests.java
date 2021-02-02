package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.ProductReview;
import org.springframework.samples.petclinic.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceMockedTests {
	
	@Mock
    private ProductRepository productRepository;
	
	protected ProductService productService;
	
	@BeforeEach
	void setup() {
		productService = new ProductService(productRepository);
	}
	
	@Test
	void shouldSaveProduct() {
		Product product = new Product();
		product.setName("Juan");
		
		Collection<Product> products1 = new ArrayList<Product>();
		products1.add(product);
		when(this.productRepository.findAll()).thenReturn(products1);
		
		Collection<Product> products2 = (Collection<Product>) this.productService.findAll();
		
		assertThat(products2).hasSize(1);
	    Product p = products2.iterator().next();
	    assertThat(p.getName()).isEqualTo("Juan");
	}
}
