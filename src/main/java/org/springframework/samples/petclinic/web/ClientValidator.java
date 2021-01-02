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
		String firstName = client.getFirstName();
		String lastName = client.getLastName();
		String address = client.getAddress();
		String city = client.getCity();
		String email = client.getEmail();
		String telephone = client.getTelephone();
		String username = client.getNameuser();
		String password = client.getPass();
		
		
		if(nif==null || nif.length()<1) {
			errors.rejectValue("nif", "Nif is null", "Nif is null");
		}
		if(firstName==null || firstName.length()<1) {
			errors.rejectValue("nif", "First name is null", "First name is null");
		}
		if(lastName == null || lastName.length()<1) {
			errors.rejectValue("nif", "Last name is null", "Last name is null");
		}
		if(address == null || address.length()<1) {
			errors.rejectValue("nif", "Address is null", "Address is null");
		}
		if(city == null || city.length()<1) {
			errors.rejectValue("nif", "City is null", "City is null");
		}
		if(email == null || email.length()<1) {
			errors.rejectValue("nif", "Email is null", "Email is null");
		}
		if(telephone == null || telephone.length()<1) {
			errors.rejectValue("nif", "Telephone is null", "Telephone is null");
		}
		if(nif.equals("[0-9]{7,8}[A-Z a-z]")) {
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



