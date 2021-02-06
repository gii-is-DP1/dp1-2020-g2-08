package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class HotelValidator implements Validator {
	private static final String REQUIRED = "required";
	
	@Override
	public void validate(Object obj, Errors errors) {
		Hotel hotel = (Hotel) obj;
		
		Integer aforo= hotel.getAforo();
		Integer ocupadas= hotel.getOcupadas();
		String city = hotel.getCity();
		
				

		
		if (aforo == null || aforo<=3) {
			errors.rejectValue("aforo",  "Aforo required and the value must be at least 4","Aforo required and the value must be at least 3");
		}
		if (city == null || city.length()<1 ) {
			errors.rejectValue("city",  "City is required ", "City is required ");
		}
		if (ocupadas == null || ocupadas<0) {
			errors.rejectValue("ocupadas", "Ocupadas is required and cant be negative value","Ocupadas is required and cant be negative value");
		}
		
		
		if ((aforo!=null && ocupadas!=null) && (ocupadas>aforo)) {
			errors.rejectValue("ocupadas", "Ocupadas value must be less than aforo value ","Ocupadas value must be less than aforo value");}
		
		
		
	}
	@Override
	public boolean supports(Class<?> clazz) {
		return Hotel.class.isAssignableFrom(clazz);
	}

}
