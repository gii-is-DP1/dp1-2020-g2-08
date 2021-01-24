package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Coupon;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ClientServiceTests {
	@Autowired
	protected ClientService clientService;
	 
	@Test
	@Transactional
	public void shouldInsertClientPositivo() {
		Collection<Client> clients =  (Collection<Client>) this.clientService.findAll();
		int found = clients.size();

		Client client = new Client();
		client.setFirstName("Usuario10");
		client.setLastName("us1");
		client.setAddress("4, Evans Street");
		client.setCity("Wollongong");
		client.setTelephone("654789123");
		client.setEmail("usuario10@gmail.com");
		client.setNif("87341935A");
		client.setNameuser("Usuario10");
		client.setPass("supersecretpassword");
		
		HashSet<Coupon> coupons = new HashSet<Coupon>();
		Coupon coupon = new Coupon();
		coupon.setDiscount(10);
		coupon.setExpireDate(LocalDate.now());
		coupons.add(coupon);
		client.setCoupons(coupons);
                User user = new User();
                user.setUsername("Usuario10");
                user.setPassword("supersecretpassword");
                user.setEnabled(true);
                client.setUser(user);                
                
		this.clientService.saveClient(client);
		assertThat(client.getId()).isNotEqualTo(0);

		clients = (Collection<Client>) this.clientService.findAll();
		assertThat(clients.size()).isEqualTo(found + 1);
	}
	
	@Test
	@Transactional
	public void shouldInsertClientNegativo1() {
		Client client = new Client();
		client.setFirstName("Usuario10");
		client.setLastName("us1");
		client.setAddress("4, Evans Street");
		client.setCity("Wollongong");
		client.setTelephone("654789123");
		client.setEmail("usuario10@gmail.com");
		client.setNif("87341935A");
		client.setNameuser("Usuario10");
		
		HashSet<Coupon> coupons = new HashSet<Coupon>();
		Coupon coupon = new Coupon();
		coupon.setDiscount(10);
		coupon.setExpireDate(LocalDate.now());
		coupons.add(coupon);
		client.setCoupons(coupons);
                User user = new User();
                user.setUsername("Usuario10");
                user.setPassword("supersecretpassword");
                user.setEnabled(true);
                client.setUser(user);                
                
        Assertions.assertThrows(ConstraintViolationException.class, ()-> {
        	client.getPass();
        	this.clientService.saveClient(client);
        });
	}
	
//	@Test
//	@Transactional
//	public void shouldInsertClientNegativo2() {
//		Client mangarmar = clientService.findById(1);
//		mangarmar.setFirstName("Usuario10");
//		mangarmar.setLastName("us1");
//		mangarmar.setNif("87341935A");
//		
//		Client client = new Client();
//		client.setFirstName("Usuario10");
//		client.setLastName("us1");
//		client.setAddress("4, Evans Street");
//		client.setCity("Wollongong");
//		client.setTelephone("654789123");
//		client.setEmail("usuario10@gmail.com");
//		client.setNif("87341935A");
//		client.setNameuser("Usuario10");
//		client.setPass("supersecretpassword");
//		
//		HashSet<Coupon> coupons = new HashSet<Coupon>();
//		Coupon coupon = new Coupon();
//		coupon.setDiscount(10);
//		coupon.setExpireDate(LocalDate.now());
//		coupons.add(coupon);
//		client.setCoupons(coupons);
//                User user = new User();
//                user.setUsername("Usuario10");
//                user.setPassword("supersecretpassword");
//                user.setEnabled(true);
//                client.setUser(user);                
//                
//        Assertions.assertThrows(DuplicatedPetNameException.class, ()-> {
//        	this.clientService.saveClient(client);
//        });
//	}

}
