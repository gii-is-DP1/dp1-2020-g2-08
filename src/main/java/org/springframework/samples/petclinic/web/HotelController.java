package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ReviewService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

	// Metodo auxiliar que devuelve el id del owner y lo mete en el modelmap. En
	// caso de no ser owner, pone un mensaje diciendo que no lo es
	public void devolverOwner(ModelMap modelmap) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object sesion = auth.getPrincipal();
		UserDetails us = null;
		if (sesion instanceof UserDetails) {
			us = (UserDetails) sesion;
		}

		String res = us.getUsername();

		if (us.getAuthorities().iterator().next().getAuthority().equals("owner")) {
			Integer id = (ownerService.findAllOwners().stream().filter(x -> x.getUser().getUsername().equals(res)))
					.collect(Collectors.toList()).get(0).getId();

			modelmap.addAttribute("message", "Tu  username Id es: " + id);
			modelmap.addAttribute("ownerId", id);
		} else {
			modelmap.addAttribute("message", "No estas logueado como owner");
		}

	}

	// SI NO ESTAS AUTENTICADO COMO OWNER, TE MANDA A TODAS LAS RESERVAS
	@GetMapping(path = "/owner/null")
	public String noEsOwner(ModelMap modelmap) {

		modelmap.addAttribute("message", "Usted no está autenticado como owner");
		return listadoReservas(modelmap);

	}

	// LISTADO DE TODAS LAS RESERVAS
	@GetMapping()
	public String listadoReservas(ModelMap modelmap) {

		// Trae todas las reservas y reseñas
		Iterable<Hotel> hotel = hotelService.findAll();
		Iterable<Booking> bookings = bookingService.findAll();
		Iterable<Review> reviews = reviewService.findAll();

		// Mete todos los datos en el modelmap para mostrarlos en la vista
		int ocupadas = bookingService.bookingCount();
		modelmap.addAttribute("reviews", reviews);
		modelmap.addAttribute("bookings", bookings);
		modelmap.addAttribute("hotel", hotel);
		modelmap.addAttribute("aforo", hotel.iterator().next().getAforo());
		modelmap.addAttribute("ocupadas", ocupadas);

		// Manda todos los atributos a la vista listaReservas.jsp
		return "hotel/listaReservas";

	}

	// LISTA DE MIS RESERVAS
	@GetMapping(path = "/myBookings")
	public String listadoMisReservas(ModelMap modelmap) {

		// Obtiene el id del owner para redirigir a la vista de reservas de ese owner
		devolverOwner(modelmap);
		return "redirect:owner/" + modelmap.getAttribute("ownerId");

	}

	// LISTADO DE RESERVAS DEL OWNER
	@GetMapping(path = "/owner/{ownerId}")
	public String listadoReservasPorOwner(@PathVariable("ownerId") int ownerId, ModelMap modelmap) {

		// Obtiene todas las reservas del owner a partir de su id
		Iterable<Booking> bookings = bookingService.findBookingByOwnerId(ownerId);
		// Pone las reservas en el modelmap para mandar a la vista
		modelmap.addAttribute("bookings", bookings);

		// Redirige a misReservas.jsp
		return "hotel/misReservas";

	}

	// CREAR UNA NUEVA RESERVA
	@GetMapping(path = "/{ownerId}/new")
	public String crearBooking(@PathVariable("ownerId") Integer ownerId, ModelMap modelmap) {

		// Crea una lista con las mascotas para que las muestre en el formulario de
		// nueva reserva
		List<Pet> pets = new ArrayList<Pet>();
		pets = ownerService.findOwnerById(ownerId).getPets();
		// Tambien obtiene el id de la url, para poder crear una reserva con ese owner
		// que recibe
		modelmap.addAttribute("booking", new Booking());
		modelmap.addAttribute("ownerId", ownerId);
		modelmap.addAttribute("pets", pets);

		// Redirige al formulario editBooking.jsp
		return "hotel/editBooking";

	}

	// CREAR UNA NUEVA RESERVA Falta hacerlo bien
	@PostMapping(path = "/save/{ownerId}")
	public String guardarBooking(@PathParam("pet") Integer pet, @PathVariable("ownerId") Integer ownerId,
			ModelMap modelmap) {

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
	public String borrarBooking(@PathVariable("bookingId") int bookingId, @PathVariable("ownerId") int ownerId,
			ModelMap modelmap) {
		Optional<Booking> booking = bookingService.findBookingById(bookingId);

		// Si la reserva está la borra, si no, te redirecciona a la lista de reservas
		if (booking.isPresent()) {
			bookingService.delete(booking.get());
			modelmap.addAttribute("message", "Booking borrado con éxito!");

		} else {
			modelmap.addAttribute("message", "Booking no encontrado");
		}

		// Cuando acaba el metodo, te redirecciona a la lista de reservas del owner
		return listadoReservasPorOwner(ownerId, modelmap);
	}

	// nueva reseña al hotel
	@GetMapping(path = "/review")
	public String crearReviewHotel(ModelMap modelmap) {

		// Manda una review vacia al formulario, para que se rellene y se mande al
		// metodo "/save"
		modelmap.addAttribute("review", new Review());
		return "reviews/newReview";

	}

	// nueva reseña al hotel
	@PostMapping(path = "/saveReview")
	public String guardarReview(@Valid Review review, BindingResult result, ModelMap modelmap) {

		// Obtiene la reseña del formulario y la guarda en la bd
		reviewService.save(review);
		modelmap.addAttribute("message", "Review creada con éxito!");

		// Cuando acaba, redirecciona a la lista de reservas, donde esta la review
		return listadoReservas(modelmap);

	}

}
