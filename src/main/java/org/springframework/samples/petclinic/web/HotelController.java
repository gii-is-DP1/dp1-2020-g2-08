package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookings")
public class HotelController {

	@Autowired
	private HotelService hotelService;
	private OwnerService ownerService;
	private PetService petService;
	@Autowired
	private BookingService bookingService;

	@Autowired
	public HotelController(HotelService hotelService, PetService petService, OwnerService ownerService,
			BookingService bookingService) {
		this.hotelService = hotelService;
		this.ownerService = ownerService;
		this.petService = petService;
		this.bookingService = bookingService;

	}

	@GetMapping()
	public String listadoReservas(ModelMap modelmap) {
		String vista = "hotel/listaReservas";
		Iterable<Hotel> hotel = hotelService.findAll();
		Iterable<Booking> bookings = bookingService.findAll();
		int ocupadas = bookingService.bookingCount();

		modelmap.addAttribute("bookings", bookings);
		modelmap.addAttribute("hotel", hotel);
		modelmap.addAttribute("aforo", hotel.iterator().next().getAforo());
		modelmap.addAttribute("ocupadas", ocupadas);

		return vista;

	}

	@GetMapping(path = "/new")
	public String crearBooking(ModelMap modelmap) {
		String vista = "hotel/editBooking";
		modelmap.addAttribute("booking", new Booking());
		return vista;

	}

	@PostMapping(path = "/save")
	public String guardarBooking(@PathParam("pet") Integer pet, @PathParam("owner") Integer owner, ModelMap modelmap) {

		Booking reservaAutogenerada = new Booking();
		reservaAutogenerada.setEndDate(LocalDate.of(2020, 10, 30));
		reservaAutogenerada.setStartDate(LocalDate.of(2020, 10, 30));
		
		reservaAutogenerada.setOwner(ownerService.findOwnerById(owner));
		reservaAutogenerada.setPet(petService.findPetById(owner));
//		
		bookingService.save(reservaAutogenerada);
		modelmap.addAttribute("message", "Booking creado con éxito!");
//		}
		
		 //hotelService.addBooking(reservaAutogenerada);
		String vista = listadoReservas(modelmap);
		return vista;

	}

	// Terminado borrado
	@GetMapping(path = "/delete/{bookingId}")
	public String borrarBooking(@PathVariable("bookingId") int bookingId, ModelMap modelmap) {
		Optional<Booking> booking = bookingService.findBookingById(bookingId);

		// Si el booking está lo borra, si no, te redirecciona a los bookings
		if (booking.isPresent()) {
			bookingService.delete(booking.get());
			modelmap.addAttribute("message", "Booking borrado con éxito!");

		} else {
			modelmap.addAttribute("message", "Booking no encontrado");
		}
		String vista = listadoReservas(modelmap);
		return vista;
	}

}
