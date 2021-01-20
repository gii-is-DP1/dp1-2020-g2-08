package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Animal;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AnimalValidator implements Validator {
	
	private static final String REQUIRED = "required";
	
	@Override
	public void validate(Object obj, Errors errors) {
		Animal animal = (Animal) obj;
		String name = animal.getName();
		// name validation
		if (!StringUtils.hasLength(name) || name.length()>50 || name.length()<3) {
			errors.rejectValue("name", REQUIRED+" and between 3 and 50 characters", REQUIRED+" and between 3 and 50 character");
		}

		// type validation
		if (animal.isNew() && animal.getType() == null) {
			errors.rejectValue("type", REQUIRED, REQUIRED);
		}

		// birth date validation
		if (animal.getBirthDate() == null) {
			errors.rejectValue("birthDate", REQUIRED, REQUIRED);
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Pet.class.isAssignableFrom(clazz);
	}

}
