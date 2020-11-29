package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ProductServiceTests {
	@Autowired
	protected ProductService productService;

	@Test
	void shouldFindProductWithCorrectId() {
		Optional<Product> product2 = this.productService.findProductById(2);
		assertThat(product2.get().getName()).startsWith("Dog´s Ball");
	}

	@Test
	@Transactional
	public void shouldInsertPetIntoDatabaseAndGenerateId() {
		Collection<Product> products = (Collection<Product>) this.productService.findAll();
		int found = products.size();

		Product product = new Product();
		product.setId(5);
		product.setCategory("Food");
		product.setInOffer("Yes");
		product.setName("Royal Canin");
		product.setPrice(17.0);
		;
		products.add(product);
		assertThat(products.size()).isEqualTo(found + 1);

		this.productService.save(product);
		;

		assertThat(product.getId()).isNotNull();
	}

	@Test
	@Transactional
	public void shouldDeleteById() {
		Collection<Product> products = (Collection<Product>) this.productService.findAll();
		int found = products.size();

		Product product = new Product();
		product.setId(1);
		product.setCategory("Pets");
		product.setInOffer("No");
		product.setName("Clown Fish");
		product.setPrice(6.0);
		this.productService.deleteById(1);

		Collection<Product> products2 = (Collection<Product>) this.productService.findAll();

		assertThat(products2.size()).isEqualTo(found - 1);

		assertThat(product.getId()).isNotNull();
	}

	@Test
	@Transactional
	public void shouldDelete() {
		Collection<Product> products = (Collection<Product>) this.productService.findAll();
		int found = products.size();

//		System.out.println("---------------------------------------------------Productos en la lista al principio: "
//				+ products.size());

		Product product = new Product();
		product.setId(1);
		product.setCategory("Pets");
		product.setInOffer("No");
		product.setName("Clown Fish");
		product.setPrice(6.0);
		this.productService.delete(product);
		Collection<Product> products2 = (Collection<Product>) this.productService.findAll();

//		System.out.println("----------------------------------------------------Productos en la lista al final: "
//				+ products2.size());
		
		
	assertThat(products2.size()).isEqualTo(found - 1);

		assertThat(product.getId()).isNotNull();
	}
}
