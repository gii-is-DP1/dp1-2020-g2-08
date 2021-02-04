package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.samples.petclinic.model.Coupon;
import org.springframework.samples.petclinic.repository.CouponRepository;

@ExtendWith(MockitoExtension.class)
class CouponServiceMockedTests {
	
	@Mock
    private CouponRepository couponRepository;
	
	protected CouponService couponService;
	
	@BeforeEach
	void setup() {
		couponService = new CouponService(couponRepository);
	}
	
	@Test
	void shouldInsertCouponPositivo() {
		HashSet<Coupon> coupons = new HashSet<Coupon>();
		Coupon coupon = new Coupon();
		coupon.setDiscount(10);
		coupon.setExpireDate(LocalDate.now());
		coupons.add(coupon);
		               
		
        Collection<Coupon> sampleCoupons =  new ArrayList<Coupon>();
        sampleCoupons.add(coupon);
        when(couponRepository.findAll()).thenReturn(sampleCoupons);
        
        Collection<Coupon> coupons1 = (Collection<Coupon>) this.couponService.findAll();
        
        assertThat(coupons1).hasSize(1);
        Coupon coupon1 = coupons1.iterator().next();
        assertThat(coupon1.getDiscount()).isEqualTo(10);
	}

}
