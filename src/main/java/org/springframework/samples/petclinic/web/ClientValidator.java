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
		
		
		if(!nif.matches("[0-9]{8,8}[A-Z]")) {
			errors.rejectValue("nif", "Nif format is not valid", "Nif format is not valid");
		}
		if(nif==null || nif.length()<1) {
			errors.rejectValue("nif", "Nif is null", "Nif is null");
		}
		if(firstName==null || firstName.length()<1) {
			errors.rejectValue("firstName", "First name is null", "First name is null");
		}
		if(lastName == null || lastName.length()<1) {
			errors.rejectValue("lastName", "Last name is null", "Last name is null");
		}
		if(address == null || address.length()<1) {
			errors.rejectValue("address", "Address is null", "Address is null");
		}
		if(city == null || city.length()<1) {
			errors.rejectValue("city", "City is null", "City is null");
		}
		if(email == null || email.length()<1) {
			errors.rejectValue("email", "Email is null", "Email is null");
		}
		if(telephone == null || telephone.length()<1) {
			errors.rejectValue("telephone", "Telephone is null", "Telephone is null");
		}
		if(telephone.length()!=9) {
			errors.rejectValue("telephone", "Telephone number must be 9 digits", "Telephone number must be 9 digits");
		}
		if(!telephone.matches("[0-9]{9,9}")) {
			errors.rejectValue("telephone", "Telephone number must be 9 digits", "Telephone number must be 9 digits");
		}	
		if(!email.matches("^(.+)@(.+)$")) {
			errors.rejectValue("email", "Wrong email format", "Wrong email format");
		}
		if(username.length()<3) {
			errors.rejectValue("nameuser", "Username is empty","Username is empty");
		}
		if(!password.matches("((?=.*\\d)(?=.*[a-zA-Z])(?=.*[~\"!@#$%?\\\\/&*\\]|\\[=()}\"{+_:;,.><\"-])).{8,}")) {
			errors.rejectValue("pass", "8 characters, mayus, minus, number and special character","8 characters, mayus, minus, number and special character");
		}
		
		
	}

	

	@Override
	public boolean supports(Class<?> clazz) {
		return Client.class.isAssignableFrom(clazz);
	}

}



