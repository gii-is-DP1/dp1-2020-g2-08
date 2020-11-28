/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 * <p>
 * We're not using Bean Validation annotations here because it is easier to define such
 * validation rule in Java.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
public class ReviewValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Review review = (Review) obj;
		
		String description = review.getDescription();
		String tittle = review.getTittle();
		Hotel hotel = review.getHotel();
		Owner owner = review.getOwner();
		LocalDate date = review.getReviewDate();
		Integer stars = review.getStars();
		// description validation
		if (!StringUtils.hasLength(description) || description.length()>200 || description.length()<3) {
			errors.rejectValue("Description", REQUIRED+" and between 3 and 200 characters", REQUIRED+" and between 3 and 200 character");
		}
		
		// description validation
				if (!StringUtils.hasLength(tittle) || tittle.length()>30 || tittle.length()<3) {
					errors.rejectValue("Tittle", REQUIRED+" and between 3 and 30 characters", REQUIRED+" and between 3 and 30 character");
				}

		// hotel validation
		if (review.isNew() && hotel == null) {
			errors.rejectValue("Hotel", REQUIRED, REQUIRED);
		}
		
//		// Owner validation
//				if (review.isNew() && owner == null) {
//					errors.rejectValue("Owner", REQUIRED, REQUIRED);
//				}

		//  date validation
		if (date == null) {
			errors.rejectValue("Review Date", REQUIRED, REQUIRED);
		}
		//Stars validation
		if (stars == null) {
			errors.rejectValue("Stars", REQUIRED, REQUIRED);
		}
		
		if (stars>5 || stars<1) {
			errors.rejectValue("Stars between 1 and 5 ", REQUIRED, REQUIRED);
		}
	}

	/**
	 * This Validator validates *just* Review instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Review.class.isAssignableFrom(clazz);
	}

}
