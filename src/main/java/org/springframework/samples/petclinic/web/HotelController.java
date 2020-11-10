package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.repository.ReviewRepository;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotel")
public class HotelController {

	@Autowired
	private HotelService hotelService;
	@Autowired
	private OwnerService ownerService;
	@Autowired
	private PetService petService;
	@Autowired
	private BookingService bookingService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	public HotelController(HotelService hotelService, PetService petService, OwnerService ownerService,
			BookingService bookingService, ReviewService reviewService) {
		this.hotelService = hotelService;
		this.ownerService = ownerService;
		this.petService = petService;
		this.bookingService = bookingService;
		this.reviewService = reviewService;

	}
	//LISTADO DE TODAS LAS RESERVAS
	@GetMapping()
	public String listadoReservas(ModelMap modelmap) {
		String vista = "hotel/listaReservas";
		Iterable<Hotel> hotel = hotelService.findAll();
		Iterable<Booking> bookings = bookingService.findAll();
		Iterable<Review> reviews = reviewService.findAll();
		int ocupadas = bookingService.bookingCount();
		modelmap.addAttribute("reviews", reviews);
		modelmap.addAttribute("bookings", bookings);
		modelmap.addAttribute("hotel", hotel);
		modelmap.addAttribute("aforo", hotel.iterator().next().getAforo());
		modelmap.addAttribute("ocupadas", ocupadas);

		return vista;

	}
	//LISTADO DE RESERVAS DEL OWNER
	@GetMapping(path="/owner/{ownerId}")
	public String listadoReservasPorOwner(@PathVariable("ownerId") int ownerId,ModelMap modelmap) {
		String vista = "hotel/misReservas";
		
		Iterable<Booking> bookings = bookingService.findBookingByOwnerId(ownerId);
		
		
		modelmap.addAttribute("bookings", bookings);
		
		

		return vista;

	}

//CREAR UNA NUEVA RESERVA
	@GetMapping(path = "/{ownerId}/new")
	public String crearBooking(@PathVariable("ownerId") Integer ownerId,ModelMap modelmap) {
		String vista = "hotel/editBooking";
		modelmap.addAttribute("booking", new Booking());
		modelmap.addAttribute("ownerId",  ownerId);
		return vista;

	}

	// CREAR UNA NUEVA RESERVA
	@PostMapping(path = "/save/{ownerId}")
	public String guardarBooking(@PathParam("pet")Integer pet, @PathVariable("ownerId") Integer ownerId,ModelMap modelmap) {

		Booking reservaAutogenerada = new Booking();
		reservaAutogenerada.setEndDate(LocalDate.of(2020, 10, 30));
		reservaAutogenerada.setStartDate(LocalDate.of(2020, 10, 30));


		
		reservaAutogenerada.setOwner(ownerService.findOwnerById(ownerId));
		reservaAutogenerada.setPet(petService.findPetById(pet));
		
		bookingService.save(reservaAutogenerada);
		modelmap.addAttribute("message", "Booking creado con éxito!");


		modelmap.clear();
		String vista = listadoReservasPorOwner(ownerId, modelmap);
		return vista;

	}

	// BORRAR UNA RESERVA
	@GetMapping(path = "/delete/{bookingId}/{ownerId}")
	public String borrarBooking(@PathVariable("bookingId") int bookingId,@PathVariable("ownerId") int ownerId, ModelMap modelmap) {
		Optional<Booking> booking = bookingService.findBookingById(bookingId);

		// Si el booking está lo borra, si no, te redirecciona a los bookings
		if (booking.isPresent()) {
			bookingService.delete(booking.get());
			modelmap.addAttribute("message", "Booking borrado con éxito!");

		} else {
			modelmap.addAttribute("message", "Booking no encontrado");
		}
		String vista = listadoReservasPorOwner(ownerId, modelmap);
		return vista;
	}

	// nueva reseña al hotel
	@GetMapping(path = "/review")
	public String crearReviewHotel(ModelMap modelmap) {
		String vista = "reviews/newReview";
		modelmap.addAttribute("review", new Review());
		return vista;

	}
	// nueva reseña al hotel
	@PostMapping(path = "/saveReview")
	public String guardarReview(@Valid Review review, BindingResult result, ModelMap modelmap) {

		reviewService.save(review);
		modelmap.addAttribute("message", "Review creada con éxito!");

		String vista = listadoReservas(modelmap);
		return vista;

	}

}
