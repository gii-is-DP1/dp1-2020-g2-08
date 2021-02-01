package org.springframework.samples.petclinic.web;


import org.springframework.samples.petclinic.model.Product;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProductValidator implements Validator {
	


	@Override
	public boolean supports(Class<?> clazz) {

		return Product.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Product product = (Product) target;
		String name = product.getName();
		Double price = product.getPrice();
		
		if (name == null || name.length()<1) {
			errors.rejectValue("name", "Name can´t be null", "Name can´t be null");
		}	
		if (price == null || price.compareTo(0.0)<0) {
			errors.rejectValue("price", "Price must be greater than 0", "Price must be greater than 0");
		}
		
	}


}