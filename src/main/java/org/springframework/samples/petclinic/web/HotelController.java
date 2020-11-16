package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

			Owner o = (ownerService.findAllOwners().stream().filter(x -> x.getUser().getUsername().equals(res)))
					.collect(Collectors.toList()).get(0);
			Integer id = o.getId();
			String name = o.getFirstName();
			String lastname = o.getLastName();

			modelmap.addAttribute("message", "Tu  username Id es: " + id);
			modelmap.addAttribute("ownerId", id);
			modelmap.addAttribute("ownerName", name);
			modelmap.addAttribute("ownerLastname", lastname);
			modelmap.addAttribute("logueado", "si");
		} else {
			modelmap.addAttribute("message", "No estas logueado como owner");
			modelmap.addAttribute("logueado", "no");
		}

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
		if (ownerService.esOwner()) {	//si es owner, muestra tus reservas, si no, vuelve a todas las reservas
			
			// Obtiene el id del owner para redirigir a la vista de reservas de ese owner
			devolverOwner(modelmap);
			
			return "redirect:owner/" + modelmap.getAttribute("ownerId");
			
		} else {
			modelmap.addAttribute("message", "Usted no está autenticado como owner");
			return listadoReservas(modelmap);

		}

	}

	// LISTADO DE RESERVAS DE CUALQUIER OWNER CON ID
	@GetMapping(path = "/owner/{ownerId}")
	public String listadoReservasPorOwner(@PathVariable("ownerId") int ownerId, ModelMap modelmap) {

		// Obtiene todas las reservas del owner a partir de su id
		List<Booking> bookings = bookingService.findBookingsByOwnerId(ownerId);
		// Pone las reservas en el modelmap para mandar a la vista
		modelmap.addAttribute("bookings", bookings);
		modelmap.addAttribute("owner", ownerService.findOwnerById(ownerId));
		

		// Redirige a misReservas.jsp
		return "hotel/misReservas";

	}

	

	

}