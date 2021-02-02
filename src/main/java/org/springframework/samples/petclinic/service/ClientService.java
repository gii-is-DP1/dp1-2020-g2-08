package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Coupon;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ClientRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	@SuppressWarnings("unchecked")
	public List<Client> findAllList() {
		return (List<Client>) clientRepo.findAll().iterator();
	}
	public Client findById(Integer clientId) {
		return clientRepo.findById(clientId).get();
	}

	@Transactional
	public int clientCount() {
		return (int) clientRepo.count();
	}
	
	public void saveClient(Client client) throws DataAccessException {
		User user = new User();
			
		user.setUsername(client.getNameuser());
		user.setPassword(client.getPass());
			
		userService.saveUser(user);	
		authoritiesService.saveAuthorities(user.getUsername(), "client");
			
		User user2 = user = userService.findUser(client.getNameuser()).get();
		client.setUser(user2);
		clientRepo.save(client);
	}
		
	@Transactional
	public boolean esClient() throws DataAccessException {
		boolean res= false;
			
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object sesion = auth.getPrincipal();
		UserDetails us = null;
		if (sesion instanceof UserDetails) {
			us = (UserDetails) sesion;
		}
		if (us.getAuthorities().iterator().next().getAuthority().equals("client")) {
			res=true;
		}
		return res;
	}
	@Transactional
	public boolean esAdminShop() throws DataAccessException {
		boolean res= false;
			
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object sesion = auth.getPrincipal();
		UserDetails us = null;
		if (sesion instanceof UserDetails) {
			us = (UserDetails) sesion;
		}
		if (us.getAuthorities().iterator().next().getAuthority().equals("adminShop")) {
			res=true;
		}
		return res;
	}	
	
	
	public Integer devolverClientId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object sesion = auth.getPrincipal();
		UserDetails us = null;
		if (sesion instanceof UserDetails) {
			us = (UserDetails) sesion;
		}
		String res = us.getUsername();		
		Client c = ( ((Collection<Client>) clientRepo.findAll()).stream().filter(x -> x.getUser().getUsername().equals(res))).collect(Collectors.toList()).get(0);
			
		Integer id =c.getId();
				
		return id;
	}


		
	public boolean tieneCupon(Coupon coupon,int clientId) {
		boolean res = true;
		Client cliente = findById(clientId);
			
		if (cliente.getCoupons().contains(coupon)) {
			res=true;
		}
		else {
			res=false;
		}
			return res;
		}
}
