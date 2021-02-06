package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Coupon;
import org.springframework.samples.petclinic.repository.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CouponServiceTests {
	
	@Autowired
	protected CouponService couponService;
	@Autowired
	protected CouponRepository couponRepository;
	
	@Test
	@Transactional
	public void shouldFindCouponByClientId() {
		Set<Coupon> coupon = this.couponService.findCouponByClientId(1);
		assertNotNull(coupon);
	}
	
	@Test
	@Transactional
	public void shouldFindCouponByClientIdList() {
		List<Coupon> coupon = this.couponService.findCouponByClientIdList(1);
		assertNotNull(coupon);
	}
	
	@Test
	@Transactional
	public void shouldDeleteById() {
		Collection<Coupon> coupons =  (Collection<Coupon>) this.couponRepository.findAll();
		int found = coupons.size();
		
		this.couponService.deleteById(3);
		
		Collection<Coupon> coupons2 =  (Collection<Coupon>) this.couponRepository.findAll();
		int found2 = coupons2.size();
		
		assertThat(found-1).isEqualTo(found2);
	}
	
	@Test
	@Transactional
	public void shouldDelete() {
		Collection<Coupon> coupons =  (Collection<Coupon>) this.couponRepository.findAll();
		int found = coupons.size();
		
		Coupon coupon = this.couponRepository.findById(1).get();
		this.couponService.delete(coupon);
		
		Collection<Coupon> coupons2 =  (Collection<Coupon>) this.couponRepository.findAll();
		int found2 = coupons2.size();
		
		assertThat(found-1).isEqualTo(found2);
	}
	
	@Test
	@Transactional
	public void shouldInsertCoupon() {
		Collection<Coupon> coupons =  (Collection<Coupon>) this.couponRepository.findAll();
		int found = coupons.size();
		
		Coupon coupon = new Coupon();
		coupon.setDiscount(10);
		coupon.setExpireDate(LocalDate.now());
		
		this.couponService.save(coupon);
		Collection<Coupon> coupons2 =  (Collection<Coupon>) this.couponRepository.findAll();
		assertThat(found+1).isEqualTo(coupons2.size());
	}
}
