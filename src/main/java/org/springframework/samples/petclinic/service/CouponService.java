package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Coupon;
import org.springframework.samples.petclinic.repository.ClientRepository;
import org.springframework.samples.petclinic.repository.CouponRepository;
import org.springframework.samples.petclinic.repository.OrderRepository;
import org.springframework.samples.petclinic.repository.ProductRepository;
import org.springframework.samples.petclinic.repository.ProductoVendidoRepository;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private ClientService clientService;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private ProductoVendidoRepository productVendRepo;
	
	@Autowired
	private CouponRepository couponRepo;
 

	
	
	@Transactional
	public Set<Coupon> findCouponByClientId(int clientId) {
		Client client = clientRepo.findById(clientId).get();
		return client.getCoupons();
	}
	
	@Transactional
	public List<Coupon> findCouponByClientIdList(int clientId) {
		Client client = clientRepo.findById(clientId).get();
		return client.getCouponslist();
	}
	

	@Transactional
	public void deleteById(int couponId) {
		couponRepo.deleteById(couponId);
	}

	@Transactional
	public void delete(Coupon coupon) {
		List<Client> clients =  (List<Client>) clientService.findAll(); 
		for (int i = 0; i < clients.size(); i++) {
			
		if (clientService.tieneCupon(coupon, clients.get(i).getId())==true) {
			clients.get(i).getCoupons().remove(coupon);
			}
		}
		couponRepo.delete(coupon);
	}
	
	@Transactional
	public void save(Coupon coupon) {
		couponRepo.save(coupon);
	}
}
