package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.ProductoVendido;
import org.springframework.samples.petclinic.repository.OrderRepository;
import org.springframework.samples.petclinic.repository.ProductoVendidoRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private ProductoVendidoRepository productVendRepo;
	
	@Autowired
	public OrderService(OrderRepository orderRepository) {
		this.orderRepo = orderRepository;
	}

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
	public List<Order> findOrderByClientId(int clientId) {

		List<Order> ordersList = (List<Order>) orderRepo.findAll();  
		
		return ordersList.stream().filter(x->x.getClient().getId().equals(clientId)).collect(Collectors.toList());
	}
	
	@Transactional
	public List<ProductoVendido> findProductsByOrder(int orderId) {
		
	
		List<ProductoVendido> list = (List<ProductoVendido>) productVendRepo.findAll();
		return list.stream().filter(x->x.getOrder().getId().equals(orderId)).collect(Collectors.toList());
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
