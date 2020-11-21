package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Shop;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.samples.petclinic.repository.HotelRepository;
import org.springframework.samples.petclinic.repository.ProductRepository;
import org.springframework.samples.petclinic.repository.ShopRepository;
import org.springframework.stereotype.Service;

@Service
public class ShopService {
	
	 @Autowired
	 private ShopRepository shopRepo;
	 @Autowired
	 private ProductRepository productRepo;
	 @Transactional
	 public int hotelCount() {
		 
		 return (int) shopRepo.count();
	 }
	 @Transactional
	 public Iterable<Shop> findAll(){
		 return shopRepo.findAll();
	 }
	 
	 @Transactional
	 public void addProduct(Product product) {

	 	shopRepo.findAll().iterator().next().getProducts().add(product);

	 }

}
