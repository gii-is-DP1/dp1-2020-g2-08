package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Coupon;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.ProductoVendido;
import org.springframework.samples.petclinic.repository.ClientRepository;
import org.springframework.samples.petclinic.repository.CouponRepository;
import org.springframework.samples.petclinic.repository.OrderRepository;
import org.springframework.samples.petclinic.repository.ProductRepository;
import org.springframework.samples.petclinic.repository.ProductoVendidoRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;

@Service
public class CouponService {

	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private ClientRepository clientRepo;
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
	public void deleteById(int couponId) {
		couponRepo.deleteById(couponId);

	}

	@Transactional
	public void delete(Coupon coupon) {
		couponRepo.delete(coupon);

	}

	@Transactional
	public void save(Coupon coupon) {
		couponRepo.save(coupon);

	}
	
}
