package org.springframework.samples.petclinic.web;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BookingValidator implements Validator {
	private static final String REQUIRED = "required";

	
	@Override
	public void validate(Object obj, Errors errors) {

		Booking booking = (Booking) obj;
		LocalDate fechaSalida = booking.getEndDate();
		Hotel hotel = booking.getHotel();
		Owner owner = booking.getOwner();
		Pet pet = booking.getPet();
		LocalDate fechaEntrada = booking.getStartDate();
		
		
		if (fechaSalida.isBefore(fechaEntrada)) {
			errors.rejectValue("endDate", REQUIRED, "La fecha de salida no puede ser anterior a la de entrada");
		}
		
		if (booking.getEndDate() == null) {
			errors.rejectValue("endDate", REQUIRED, "Se requiere fecha de entrada");
		}
		
		if (booking.getStartDate() == null) {
			errors.rejectValue("startDate", REQUIRED, "Se requiere fecha de salida");
		}
		if (booking.getHotel() == null) {
			errors.rejectValue("hotel", REQUIRED, REQUIRED);
		}
//		if (booking.getOwner() == null) {
//			errors.rejectValue("El campo owner", REQUIRED, REQUIRED);
//		}
		if (booking.getPet() == null) {
			errors.rejectValue("pet", REQUIRED, REQUIRED);
		}
		
		
//		if (Period.between(fechaEntrada, fechaSalida.plusDays(1)).getDays()>7) {
//			errors.rejectValue("La reserva no puede durar mas de 7 dias", REQUIRED, REQUIRED);
//		}
		
		if (booking.getPet().getType().getName().equals("dog")) {
			errors.rejectValue("pet", "La mascota no puede ser un perro", "La mascota no puede ser un perro");
		}
		
		
		}

	

	@Override
	public boolean supports(Class<?> clazz) {
		return Booking.class.isAssignableFrom(clazz);
	}

}
