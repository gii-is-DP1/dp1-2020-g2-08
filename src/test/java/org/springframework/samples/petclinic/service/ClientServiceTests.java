package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ClientServiceTests {
	@Autowired
	protected ClientService clientService;
	 
	@Test
	@Transactional
	public void shouldInsertClient() {
		Collection<Client> clients =  (Collection<Client>) this.clientService.findAll();
		int found = clients.size();

		Client client = new Client();
		client.setFirstName("Usuario1");
		client.setLastName("us1");
		client.setAddress("4, Evans Street");
		client.setCity("Wollongong");
		client.setTelephone("654789123");
		client.setEmail("usuario1@gmail.com");
		client.setNif("87341935A");
                User user=new User();
                user.setUsername("Usuario1");
                user.setPassword("supersecretpassword");
                user.setEnabled(true);
                client.setUser(user);                
                
		this.clientService.saveClient(client);
		assertThat(client.getId().longValue()).isNotEqualTo(0);

		clients = (Collection<Client>) this.clientService.findAll();
		assertThat(clients.size()).isEqualTo(found + 1);
	}

}
