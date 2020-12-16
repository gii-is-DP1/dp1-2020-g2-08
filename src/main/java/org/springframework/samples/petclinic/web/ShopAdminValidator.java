package org.springframework.samples.petclinic.web;


import org.springframework.samples.petclinic.model.Product;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ShopAdminValidator implements Validator {
	
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
			errors.rejectValue("name", REQUIRED, "Name is required");
		}
		
		if (price == null) {
			errors.rejectValue("price", REQUIRED, "Price is required");
		}
		
		if (!StringUtils.hasLength(name) || name.length()<3 || name.length()>50) {
			errors.rejectValue("name", "Name length must be between 3 and 50", "Name length must be between 3 and 50");
		}
		
		if (price == null || price <= 0) {
			errors.rejectValue("price", "Price is required", "Price must be greater than 0");
		}

	}


}

