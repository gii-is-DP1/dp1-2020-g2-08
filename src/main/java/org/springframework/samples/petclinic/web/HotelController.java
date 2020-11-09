package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
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
@RequestMapping("/bookings")
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
			BookingService bookingService,ReviewService reviewService) {
		this.hotelService = hotelService;
		this.ownerService = ownerService;
		this.petService = petService;
		this.bookingService = bookingService;
		this.reviewService=reviewService;

	}

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

		// hotelService.addBooking(reservaAutogenerada);
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

	// nueva reseña al hotel
	@GetMapping(path = "/review")
	public String crearReviewHotel(ModelMap modelmap) {
		String vista = "reviews/newReview";
		modelmap.addAttribute("review", new Review());
		return vista;

	}

	@PostMapping(path = "/saveReview")
	public String guardarReview(@Valid Review review,BindingResult result, ModelMap modelmap) {
		
		reviewService.save(review);
		modelmap.addAttribute("message", "Review creada con éxito!");


		String vista = listadoReservas(modelmap);
		return vista;

	}

}
