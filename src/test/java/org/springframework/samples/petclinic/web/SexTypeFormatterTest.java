package org.springframework.samples.petclinic.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.SexType;
import org.springframework.samples.petclinic.service.AnimalService;
import org.springframework.samples.petclinic.service.PetService;

@ExtendWith(MockitoExtension.class)
public class SexTypeFormatterTest {
	
	@Mock
	private AnimalService clinicService;

	private SexTypeFormatter sexTypeFormatter;

	@BeforeEach
	void setup() {
		sexTypeFormatter = new SexTypeFormatter(clinicService);
	}

	@Test
	void testPrint() {
		SexType sexType = new SexType();
		sexType.setName("Hamster");
		String sexTypeName = sexTypeFormatter.print(sexType, Locale.ENGLISH);
		assertEquals("Hamster", sexTypeName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(clinicService.findSexTypes()).thenReturn(makeSexTypes());
		SexType sexType = sexTypeFormatter.parse("Female", Locale.ENGLISH);
		assertEquals("Female", sexType.getName());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(clinicService.findSexTypes()).thenReturn(makeSexTypes());
		Assertions.assertThrows(ParseException.class, () -> {
			sexTypeFormatter.parse("Other", Locale.ENGLISH);
		});
	}

	/**
	 * Helper method to produce some sample pet types just for test purpose
	 * @return {@link Collection} of {@link PetType}
	 */
	private Collection<SexType> makeSexTypes() {
		Collection<SexType> sexTypes = new ArrayList<>();
		sexTypes.add(new SexType() {
			{
				setName("Male");
			}
		});
		sexTypes.add(new SexType() {
			{
				setName("Female");
			}
		});
		return sexTypes;
	}


}
