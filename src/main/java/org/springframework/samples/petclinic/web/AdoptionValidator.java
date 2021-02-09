package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Animal;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AdoptionValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Adoption adoption = (Adoption) obj;
		if(adoption.getAnimal() == null) {
			Animal animal = new Animal();
			animal.setName("");
			adoption.setAnimal(animal);
		}
		String name = adoption.getAnimal().getName();
		// name validation
		if (!StringUtils.hasLength(name) || name.length() > 50 || name.length() < 3) {
			errors.rejectValue("name", REQUIRED + " and between 3 and 50 characters",
					REQUIRED + " and between 3 and 50 character");
		}

	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Adoption.class.isAssignableFrom(clazz);
	}

}
