package org.springframework.samples.petclinic.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.repository.ClientRepository;
import org.springframework.samples.petclinic.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepo;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public ClientService(ClientRepository clientRepository, UserService userService, AuthoritiesService authoritiesService) {
		this.clientRepo = clientRepository;
	}

	
	public Iterable<Client> findAll() {
		return clientRepo.findAll();
	}

	@Transactional
	public int clientCount() {
		return (int) clientRepo.count();
	}
	
		public void saveClient(Client client) throws DataAccessException {
			//creating owner
			clientRepo.save(client);		
			//creating user
			userService.saveUser(client.getUser());
			//creating authorities
			authoritiesService.saveAuthorities(client.getUser().getUsername(), "client");


	}


}
