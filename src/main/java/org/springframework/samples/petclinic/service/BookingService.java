package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import static java.time.temporal.ChronoUnit.DAYS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.samples.petclinic.repository.HotelRepository;
import org.springframework.stereotype.Service;

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

	public boolean numeroDiasValido(Booking booking) {

		long dias = DAYS.between(booking.getStartDate(), booking.getEndDate());

		if ((dias < 1) || (dias > 7)) {
			return false;
		} else {
			return true;
		}

	}

	public boolean fechaValida(Booking booking) {
		if (booking.getStartDate().isBefore(booking.getEndDate())) {
			return true;
		} else {
			return false;
		}
	}

}
