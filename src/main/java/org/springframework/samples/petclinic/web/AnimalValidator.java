package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Animal;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.ShelterService;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AnimalValidator implements Validator {
	
	private static final String REQUIRED = "required";
	private final ShelterService shelterService = new ShelterService();
	
	@Override
	public void validate(Object obj, Errors errors) {
		Animal animal = (Animal) obj;
		String name = animal.getName();
		// name validation
		if (!StringUtils.hasLength(name) || name.length()>50 || name.length()<3) {
			errors.rejectValue("name", REQUIRED+" and between 3 and 50 characters", REQUIRED+" and between 3 and 50 character");
		}

		// type validation
		if (animal.getType() == null) {
			errors.rejectValue("type", REQUIRED, REQUIRED);
		}

		// birth date validation
		if (animal.getBirthDate() == null || animal.getBirthDate().isAfter(LocalDate.now())) {
			errors.rejectValue("birthDate", REQUIRED+ " and must be a valid date", REQUIRED+ " and must be a valid date");
		}
		
		if(animal.getSex() == null ) {
			errors.rejectValue("sex", REQUIRED, REQUIRED);	
		}
		
		if(animal.getShelterDate() == null || animal.getShelterDate().isAfter(LocalDate.now())) {
			errors.rejectValue("shelterDate", REQUIRED+ " and must be a valid date", REQUIRED+ " and must be a valid date");
		}
		
//		if(animal.isNew() && (animal.getDescription() == null)) {
//			errors.rejectValue("description", REQUIRED, REQUIRED);
//		}
		
//		if(animal.isNew() && (animal.getImageUrl() == null )) {
//			errors.rejectValue("imageUrl", REQUIRED, REQUIRED);
//		}
//		
//		if(animal.getShelter() == null || animal.getShelter().getId() == null) {
//			errors.rejectValue("shelter", REQUIRED, REQUIRED);
//		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Animal.class.isAssignableFrom(clazz);
	}

}
