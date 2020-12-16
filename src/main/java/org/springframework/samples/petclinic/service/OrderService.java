package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.repository.OrderRepository;
import org.springframework.samples.petclinic.repository.ProductRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private ProductRepository productRepo;
	
 
	@Transactional
	public int orderCount() {
		return (int) orderRepo.count();
	}

	public Iterable<Order> findAll() {
		return orderRepo.findAll();
	}

	@Transactional
	public Optional<Order> findOrderById(int orderId) {
		return orderRepo.findById(orderId);
	}
	


	@Transactional
	public void deleteById(int orderId) {
		orderRepo.deleteById(orderId);

	}

	@Transactional
	public void delete(Order order) {
		orderRepo.delete(order);

	}

	@Transactional
	public void save(Order order) {
		orderRepo.save(order);

	}
	
}
