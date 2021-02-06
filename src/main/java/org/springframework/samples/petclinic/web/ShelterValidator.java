package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Shelter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ShelterValidator implements Validator{
	
private static final String REQUIRED = "required";
	
	@Override
	public void validate(Object obj, Errors errors) {
		Shelter shelter = (Shelter) obj;
		
		Integer aforo= shelter.getAforo();
		String city = shelter.getCity();
		
				

		
		if (aforo == null || aforo<=3) {
			errors.rejectValue("aforo",  "Aforo required and the value must be at least 4","Aforo required and the value must be at least 3");
		}
		if (city == null || city.length()<1 || city == "" ) {
			errors.rejectValue("city",  "City is required ", "City is required ");
		}
		
		
		
		
		
		
	}
	@Override
	public boolean supports(Class<?> clazz) {
		return Shelter.class.isAssignableFrom(clazz);
	}


}
