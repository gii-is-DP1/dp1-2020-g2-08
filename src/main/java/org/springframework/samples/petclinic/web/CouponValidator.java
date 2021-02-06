package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Coupon;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CouponValidator implements Validator {
	private static final String REQUIRED = "required";
	
	@Override
	public void validate(Object obj, Errors errors) {
		Coupon coupon = (Coupon) obj;
		
		Integer discount = coupon.getDiscount();
		LocalDate expireDate = coupon.getExpireDate();
		
				

		
		if (discount == null || discount<0 || discount>99) {
			errors.rejectValue("discount",  "Discount required and the value must be at least 1 and the maximum value must be 99","Discount required and the value must be at least 1 and the maximum value must be 99");
		}
		if (expireDate== null ||expireDate.isBefore(LocalDate.now()) ) {
			errors.rejectValue("expireDate",  "Expire Date required cant be past","Expire Date required cant be past");
		}
		
		
		
	}
	@Override
	public boolean supports(Class<?> clazz) {
		return Coupon.class.isAssignableFrom(clazz);
	}

}
