package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Product;
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
	
}
