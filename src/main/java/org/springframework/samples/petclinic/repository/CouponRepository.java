package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Coupon;

public interface CouponRepository extends CrudRepository<Coupon,Integer>{
	
	

}
