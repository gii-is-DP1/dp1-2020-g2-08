package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BookingValidator implements Validator {
	private static final String REQUIRED = "required";
	
	@Override
	public void validate(Object obj, Errors errors) {

		Booking booking = (Booking) obj;
		LocalDate fechaSalida = booking.getEndDate();
		LocalDate fechaEntrada = booking.getStartDate();
		
		
//		if (fechaEntrada.getDayOfYear()<=(LocalDate.now().getDayOfYear())) {
//			errors.rejectValue("startDate", "La fecha de entrada como minimo tiene que ser mañana","La fecha de entrada como minimo tiene que ser mañana");
//		}
//		
		
		
		
		if (fechaSalida.isBefore(fechaEntrada)) {
			errors.rejectValue("endDate", "La fecha de salida no puede ser anterior a la de entrada", "La fecha de salida no puede ser anterior a la de entrada");
		}
		
		if (booking.getEndDate().toString() == "") {
			errors.rejectValue("endDate", REQUIRED, REQUIRED);
		}
		
		if (booking.getStartDate().toString() == "") {
			errors.rejectValue("startDate", REQUIRED, REQUIRED);
		}
	

		if (booking.getPet() == null) {
			errors.rejectValue("pet", REQUIRED, REQUIRED);
		}
		
		
		if (Period.between(fechaEntrada, fechaSalida.plusDays(1)).getDays()>8 ) {
			errors.rejectValue("endDate", "La reserva no puede durar mas de 7 dias", "La reserva no puede durar mas de 7 dias");
		}
		
		if ( Period.between(fechaEntrada, fechaSalida.plusDays(1)).getDays()<2) {
			errors.rejectValue("endDate", "La reserva tiene que durar al menos 1 dia", "La reserva tiene que durar al menos 1 dia");
		}
		
		if (booking.getPet().getType().getName().equals("lizard")) {
			errors.rejectValue("pet", "La mascota no puede ser un lizard", "La mascota no puede ser un lizard");
		}
		if (booking.getPet().getType().getName().equals("snake")) {
			errors.rejectValue("pet", "La mascota no puede ser una serpiente", "La mascota no puede ser una serpiente");
		}
		
//		if (bookingService.estaOcupadoEnRango(booking.getStartDate(), booking.getEndDate(), booking.getHotel().getId())) {
//			errors.rejectValue("startDate", "La fecha seleccionada no está disponible", "La fecha seleccionada no está disponible");
//		}
	
		
	
	} 

	

	@Override
	public boolean supports(Class<?> clazz) {
		return Booking.class.isAssignableFrom(clazz);
	}

}
