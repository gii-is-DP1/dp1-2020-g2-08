package org.springframework.samples.petclinic.web;


import org.springframework.samples.petclinic.model.Product;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProductValidator implements Validator {
	
	private static final String REQUIRED = "required";

	@Override
	public boolean supports(Class<?> clazz) {

		return Product.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Product product = (Product) target;
		String name = product.getName();
		Double price = product.getPrice();
		
		if (name == null ) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}
		
		if (price == null) {
			errors.rejectValue("price", REQUIRED, REQUIRED);
		}
		
		if(price.isNaN()) {
			errors.rejectValue("price", "ssss");
		}
		
		if (!StringUtils.hasLength(name) || name.length()<3 || name.length()>50) {
			errors.rejectValue("name", "Name length must be between 3 and 50", "Name length must be between 3 and 50");
		}
		
		if (price == 0 || price < 0) {
			errors.rejectValue("price", "Price must be greater than 0", "Price must be greater than 0");
		}

	}


}