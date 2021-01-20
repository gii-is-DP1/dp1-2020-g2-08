package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.ProductReview;
import org.springframework.samples.petclinic.repository.ProductRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Transactional
	public int productCount() {
		return (int) productRepo.count();
	}

	public Iterable<Product> findAll() {
		return productRepo.findAll();
	}

	@Transactional
	public Optional<Product> findProductById(int productId) {
		return productRepo.findById(productId);
	}

	@Transactional
	public void deleteById(int productId) {
		productRepo.deleteById(productId);

	}

	@Transactional
	public void delete(Product product) {
		productRepo.delete(product);

	}

	@Transactional
	public void save(Product product) {
		productRepo.save(product);
	}

	@Transactional
	public Product findProductByName(String name){
		 List<Product> products = (List<Product>) productRepo.findAll();
		 	return products.stream().filter(x->x.getName().equals(name)).findFirst().get();
	}
	
//	public void addRate(ProductReview productReview) {
//
//		String name = productReview.getProductoVendido().getNombre();
//		List<Product> productList = (List<Product>) findAll();
//		Product product = productList.stream().filter(x -> x.getName().startsWith(name)).findFirst().get();
//		List<Integer> rates = product.getRateList();
//		rates.add(productReview.getStars());
//		product.setRateList(rates);
//		productRepo.save(product);
//
//	}

}
