package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Coupon;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ClientRepository;


@ExtendWith(MockitoExtension.class)
class ClientServiceMockedTests {

	@Mock
    private ClientRepository clientRepository;
	@Mock
    private UserService userService;
	@Mock
    private AuthoritiesService authorithiesService;
	
	protected ClientService clientService;
	
	@BeforeEach
	void setup() {
		clientService = new ClientService(clientRepository,userService,authorithiesService);
	}
	
	@Test
	void shouldInsertClientPositivo() {
		Client sampleClient = new Client();
		sampleClient.setFirstName("Usuario10");
		sampleClient.setLastName("us1");
		sampleClient.setAddress("4, Evans Street");
		sampleClient.setCity("Wollongong");
		sampleClient.setTelephone("654789123");
		sampleClient.setEmail("usuario10@gmail.com");
		sampleClient.setNif("87341935A");
		sampleClient.setNameuser("Usuario10");
		sampleClient.setPass("supersecretpassword");
		
		HashSet<Coupon> coupons = new HashSet<Coupon>();
		Coupon coupon = new Coupon();
		coupon.setDiscount(10);
		coupon.setExpireDate(LocalDate.now());
		coupons.add(coupon);
		sampleClient.setCoupons(coupons);
                User user = new User();
                user.setUsername("Usuario10");
                user.setPassword("supersecretpassword");
                user.setEnabled(true);
                sampleClient.setUser(user);                
		
        Collection<Client> sampleClients =  new ArrayList<Client>();
        sampleClients.add(sampleClient);
        when(clientRepository.findAll()).thenReturn(sampleClients);
        
        Collection<Client> clients = (Collection<Client>) this.clientService.findAll();
        
        assertThat(clients).hasSize(1);
        Client client = clients.iterator().next();
        assertThat(client.getFirstName()).isEqualTo("Usuario10");
        assertThat(client.getLastName()).isEqualTo("us1");
	}
	
	@Test
	public void shouldInsertClientNegativo1() {
		Client sampleClient = new Client();
		sampleClient.setFirstName("Usuario10");
		sampleClient.setLastName("us1");
		sampleClient.setAddress("4, Evans Street");
		sampleClient.setCity("Wollongong");
		sampleClient.setTelephone("654789123");
		sampleClient.setEmail("usuario10@gmail.com");
		sampleClient.setNif("87341935A");
		sampleClient.setNameuser("Usuario10");
		
		HashSet<Coupon> coupons = new HashSet<Coupon>();
		Coupon coupon = new Coupon();
		coupon.setDiscount(10);
		coupon.setExpireDate(LocalDate.now());
		coupons.add(coupon);
		sampleClient.setCoupons(coupons);
                User user = new User();
                user.setUsername("Usuario10");
                user.setPassword("supersecretpassword");
                user.setEnabled(true);
                sampleClient.setUser(user);   
                
        Collection<Client> sampleClients =  new ArrayList<Client>();
        sampleClients.add(sampleClient);
        when(clientRepository.findAll()).thenReturn(sampleClients);
                
        Collection<Client> clients = (Collection<Client>) this.clientService.findAll();
        
        assertThat(clients).hasSize(1);
        Client client = clients.iterator().next();
                
        assertThat(client.getPass()).isEqualTo(null);
        assertThrows(NullPointerException.class, () -> {
        	client.getPass();
        	this.clientService.saveClient(client);
        });
        		
	}
}
