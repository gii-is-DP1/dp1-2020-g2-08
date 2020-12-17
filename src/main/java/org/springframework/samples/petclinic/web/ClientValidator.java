package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ClientValidator implements Validator {
	
	
private static final String REQUIRED = "required";
	
	public void validate(Object obj, Errors errors) {

		Client client = (Client) obj;
		String nif = client.getNif();
		String username = client.getNameuser();
		String password = client.getPass();
		
		
		if(nif==null || nif.equals("[0-9]{7,8}[A-Z a-z]")) {
			errors.rejectValue("nif", "Wrong nif format", "Wrong nif format");
		}
		if(username.length()<3) {
			errors.rejectValue("nameuser", "Username is empty","Username is empty");
		}
		if(password.length()<3) {
			errors.rejectValue("pass", "Password is empty","Password is empty");
		}
		
		
	}

	

	@Override
	public boolean supports(Class<?> clazz) {
		return Client.class.isAssignableFrom(clazz);
	}

}



