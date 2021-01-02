package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Coupon;

public interface CouponRepository extends CrudRepository<Coupon,Integer>{

}
