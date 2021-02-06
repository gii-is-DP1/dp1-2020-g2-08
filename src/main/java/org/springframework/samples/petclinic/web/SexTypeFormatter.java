package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.SexType;
import org.springframework.samples.petclinic.service.AnimalService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Component;

@Component
public class SexTypeFormatter implements Formatter<SexType>{
	
	private final AnimalService anService;

	@Autowired
	public SexTypeFormatter(AnimalService animalService) {
		this.anService = animalService;
	}

	@Override
	public String print(SexType animalType, Locale locale) {
		return animalType.getName();
	}

	@Override
	public SexType parse(String text, Locale locale) throws ParseException {
		Collection<SexType> findSexTypes = this.anService.findSexTypes();
		for (SexType type : findSexTypes) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}



