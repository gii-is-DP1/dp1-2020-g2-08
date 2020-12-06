package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.samples.petclinic.repository.HotelRepository;
import org.springframework.stereotype.Service;

import javassist.expr.NewArray;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepo;

	@Autowired
	private HotelRepository hotelRepo;

	@Transactional
	public int bookingCount() {

		return (int) bookingRepo.count();
	}

	@Transactional
	public Iterable<Booking> findAll() {
		return bookingRepo.findAll();
	}

	@Transactional
	public Booking findBookingById(int bookingId) {
		return bookingRepo.findById(bookingId).get();
	}

	@Transactional
	public List<Booking> findBookingsByOwnerId(int ownerId) {
		List<Booking> lista = new ArrayList<Booking>();
		lista = (List<Booking>) bookingRepo.findAll();
		return lista.stream().filter(x -> x.getOwner().getId().equals(ownerId)).collect(Collectors.toList());
	}

	public Integer numeroBookingsPorOwner(int ownerId) {
		List<Booking> lista = new ArrayList<Booking>();
		lista = (List<Booking>) bookingRepo.findAll();
		return lista.stream().filter(x -> x.getOwner().getId().equals(ownerId)).collect(Collectors.toList()).size();
	}

	public Integer numeroBookingsPorPet(int petId) {
		List<Booking> lista = new ArrayList<Booking>();
		lista = (List<Booking>) bookingRepo.findAll();
		return lista.stream().filter(x -> x.getPet().getId().equals(petId)).collect(Collectors.toList()).size();
	}

	@Transactional
	public List<Booking> findBookingsByHotelId(int hotelId) {
		List<Booking> lista = new ArrayList<Booking>();
		lista = (List<Booking>) bookingRepo.findAll();
		return lista.stream().filter(x -> x.getHotel().getId().equals(hotelId)).collect(Collectors.toList());
	}

	@Transactional
	public void deleteById(int bookingId) {
		bookingRepo.deleteById(bookingId);

	}

	@Transactional
	public void delete(Booking booking) {
		bookingRepo.delete(booking);

	}

	@Transactional
	public void delete(Integer bookingId) {
		bookingRepo.deleteById(bookingId);

	}

	@Transactional
	public void save(Booking booking) {
		bookingRepo.save(booking);

	}

	@Transactional
	public void eliminarBookingsPorHotel(Integer hotelId) {
		List<Booking> bookings = (List<Booking>) findAll();
		List<Integer> idBooking = bookings.stream().filter(x -> x.getHotel().getId().equals(hotelId))
				.map(x -> x.getId()).collect(Collectors.toList()); // Lista con los id de los bookings a borrar

		if (idBooking.size() > 0) {
			// Borrado de todos los bookings asociados al hotel seleccionado
			for (int i = 0; i < idBooking.size(); i++) {
				deleteById(idBooking.get(i));

			}
		}
	}
//
//	public boolean numeroDiasValido(Booking booking) {
//
//		long dias = DAYS.between(booking.getStartDate(), booking.getEndDate());
//
//		if ((dias < 1) || (dias > 7)) {
//			return false;
//		} else {
//			return true;
//		}
//
//	}
//
//	public boolean fechaValida(Booking booking) {
//		if (booking.getStartDate().isBefore(booking.getEndDate())) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	public Map<LocalDate, Integer> ocupacionesPorDias(Integer hotelId) {
		Map<LocalDate, Integer> ocupaciones = new HashMap<LocalDate, Integer>();

		List<Booking> bookings = findBookingsByHotelId(hotelId);

		for (int i = 0; i < bookings.size(); i++) {
			Booking b = bookings.get(i);

			Integer dias = Period.between(b.getStartDate(), b.getEndDate().plusDays(1)).getDays();
			for (int j = 0; j < dias; j++) {
				LocalDate date = b.getStartDate().plusDays(j);
				if (ocupaciones.containsKey(date)) {
					ocupaciones.put(date, ocupaciones.get(date) + 1);
				} else {
					ocupaciones.put(date, 1);

				}

			}
		}
		return ocupaciones;

	}

	public List<LocalDate> diasOcupados(Integer aforo, Integer hotelId) {

		List<LocalDate> ocupados = new ArrayList<LocalDate>();
		ocupados = ocupacionesPorDias(hotelId).entrySet().stream().filter(x -> x.getValue() >= aforo)
				.map(x -> x.getKey()).collect(Collectors.toList());

		return ocupados;

	}

	public boolean estaOcupadoEnRango(LocalDate startDate, LocalDate endDate, Integer hotelId) {
		Integer dias = Period.between(startDate, endDate.plusDays(1)).getDays();
		List<LocalDate> diasOcupados = diasOcupados(4, hotelId);

		boolean res = false;
		for (int i = 0; i < dias; i++) {

			if (diasOcupados.contains(startDate.plusDays(i))) {

				res = true;

			}

		}

		return res;

	}

	public List<String> diasOcupadosStr(Integer aforo, Integer hotelId) {

		List<String> ocupados = new ArrayList<String>();
		ocupados = ocupacionesPorDias(hotelId).entrySet().stream().filter(x -> x.getValue() >= aforo)
				.map(x -> x.getKey().toString()).collect(Collectors.toList());

		return ocupados;

	}

	public List<String> parseaDates(List<String> lista) {
		List<String> res = new ArrayList<String>();

		for (int i = 0; i < lista.size(); i++) {
			res.add(lista.get(i).replace("-", ","));
		}

		return res;
	}

	public List<String> parseaDates2(List<LocalDate> lista) {
		List<String> res = new ArrayList<String>();

		for (int i = 0; i < lista.size(); i++) {
			int dia = lista.get(i).getDayOfMonth();
			String mes = lista.get(i).getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
			int anyo = lista.get(i).getYear();

			res.add(dia + " de " + mes + " del " + anyo);
		}

		return res;
	}
    //   new Date(2020,12,29).getDate() == date.getDate() || new Date( ).getDate() == date.getDate()
	public String restriccionCalendario(List<String> lista) {
		String res ="new Date("+lista.get(0).replace("-",",")+").getDate() == date.getDate() ";
		
		
		for (int i = 1; i < lista.size(); i++) {
			res = res+" || new Date("+lista.get(i).replace("-",",")+" ).getDate() == date.getDate()";
		}
		
		
		return res ;
		
	}

}
