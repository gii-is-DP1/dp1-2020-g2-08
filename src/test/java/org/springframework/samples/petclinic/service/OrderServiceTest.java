package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.ProductoVendido;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OrderServiceTest {

	@Autowired
	protected OrderService orderService;
	@Autowired
	protected ClientService clientService;
	@Autowired
	protected CouponService couponService;
	
	@Test
	@Transactional
	public void findOrderById() {
		Optional<Order> productList = this.orderService.findOrderById(1);
		assertNotNull(productList);
		assertThat(productList.get().getCity()).isEqualTo("Sevilla");
	}
	
	@Test
	@Transactional
	public void findOrderByClientId() {
		List<Order> productList = this.orderService.findOrderByClientId(1);
		assertNotNull(productList);
		assertThat(productList.get(0).getCity()).isEqualTo("Sevilla");
	}
	
	@Test
	@Transactional
	public void findProductsWhithCorrectOrderId() {

		List<ProductoVendido> productList = this.orderService.findProductsByOrder(1);
		assertNotNull(productList);
	}

	@Test
	@Transactional
	public void deleteById() {
		Collection<Order> pedidos = (Collection<Order>) this.orderService.findAll();
		this.orderService.deleteById(2);
		Collection<Order> pedidos2 = (Collection<Order>) this.orderService.findAll();
		assertThat(pedidos.size()-1).isEqualTo(pedidos2.size());
	}
	
	@Test
	@Transactional
	public void delete() {
		Collection<Order> pedidos = (Collection<Order>) this.orderService.findAll();
		Order order = this.orderService.findOrderById(2).get();
		this.orderService.delete(order);
		Collection<Order> pedidos2 = (Collection<Order>) this.orderService.findAll();
		assertThat(pedidos.size()-1).isEqualTo(pedidos2.size());
	}
	
	@Test
	@Transactional
	public void saveOrder() {
		Collection<Order> pedidos = (Collection<Order>) this.orderService.findAll();
		
		Order order = new Order();
		order.setId(5);
		order.setAddress("");
		order.setCity("");
		order.setCountry("");
		order.setDeliveryDate(LocalDate.now());
		order.setOrderDate(LocalDate.of(2022, 10, 30));
		order.setPostalCode(06220);
		order.setPriceOrder(10.0);
		order.setState("");
		order.setClient(this.clientService.findById(1));
		order.setCoupon(this.couponService.findCouponByClientIdList(1).get(0));
		
		this.orderService.save(order);
		
		Collection<Order> pedidos2 = (Collection<Order>) this.orderService.findAll();
	
		assertThat(pedidos2.size()).isEqualTo(pedidos.size()+1);
	}
	
//	@Test
//	@Transactional
//	public void saveOrderError() {
//		
//		Order order = new Order();
//		
//		this.orderService.save(order);
//		
//		Assertions.assertThrows(ConstraintViolationException.class, ()-> {
//        	order.getAddress();
//        	this.orderService.save(order);
//        });
//	}
	
}
