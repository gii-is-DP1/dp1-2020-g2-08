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
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceMockedTests {

	@Mock
    private OrderRepository orderRepository;
	
	protected OrderService orderService;
	
	@BeforeEach
	void setup() {
		orderService = new OrderService(orderRepository);
	}
	
	@Test
	void saveOrder() {
		Order order = new Order();
		order.setCity("Sevilla");
		Collection<Order> pedidos = new ArrayList<Order>();
		pedidos.add(order);
		
		when(this.orderRepository.findAll()).thenReturn(pedidos);
		
		
		Collection<Order> pedidos1 = (Collection<Order>) this.orderService.findAll();
		
		assertThat(pedidos1).hasSize(1);
	    Order pedido = pedidos1.iterator().next();
		assertThat(pedido.getCity()).isEqualTo("Sevilla");

	}
}
