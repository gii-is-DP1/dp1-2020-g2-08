package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Coupon;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class OrderValidator implements Validator {
	private static final String REQUIRED = "required";
	
	@Override
	public void validate(Object obj, Errors errors) {
		Order order = (Order) obj;
	//address, city, country, postal code, coupon

		String address = order.getAddress();
		String city = order.getCity();
		String country = order.getCountry();
		Integer postalCode = order.getPostalCode();
		Coupon coupon = order.getCoupon();
		
		if (address.length()<5 || address.length()>50) {
			errors.rejectValue("address",  "Address is required and It must contain between 5 and 50 characters.","Address is required and It must contain between 1 and 50 characters.");
		}
		
		if (city.length()<2 || city.length()>50) {
			errors.rejectValue("city",  "City is required and It must contain between 2 and 50 characters.","City is required and It must contain between 1 and 50 characters.");
		}
		
		if (country.length()<2 || country.length()>50) {
			errors.rejectValue("country", "Country is required and It must contain between 2 and 50 characters.","Country is required and It must contain between 1 and 50 characters.");
		}
	

		if (postalCode == null) {
			errors.rejectValue("postalCode", "PostalCode is required","PostalCode is required");
		}
		
		
		
		if (coupon!=null) {
			if (coupon.getExpireDate().isBefore(LocalDate.now())) {
		errors.rejectValue("coupon", "The selected coupon has already expired, try another one", "The selected coupon has already expired, try another one");
	}
		
		
			
		}
	}


	@Override
	public boolean supports(Class<?> clazz) {
		return Order.class.isAssignableFrom(clazz);
	}

}
