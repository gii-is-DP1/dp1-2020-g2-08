package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
}
