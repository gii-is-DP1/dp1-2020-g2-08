package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ReviewService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/hotel")
public class HotelController {

	@Autowired
	private HotelService hotelService;
	@Autowired
	private OwnerService ownerService;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private ReviewService reviewService;

	@InitBinder("hotel")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new HotelValidator());
	}
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@Autowired
	public HotelController(HotelService hotelService, OwnerService ownerService, BookingService bookingService,
			ReviewService reviewService) {
		this.hotelService = hotelService;
		this.ownerService = ownerService;

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

			modelmap.addAttribute("ownerId", id);
			modelmap.addAttribute("owner", o);

		} else {
			modelmap.addAttribute("message", "No estas logueado como owner");

		}

	}

	// LISTADO DE TODAS LAS RESERVAS
	@GetMapping()
	public String listadoReservas(ModelMap modelmap) {

		// Trae todas las reservas y reseñas
		Iterable<Hotel> hotel = hotelService.findAll();
		Iterable<Booking> bookings = bookingService.findAll();
		Iterable<Review> reviews = reviewService.findAll();

		if (hotel.iterator().hasNext()) {
			// Mete todos los datos en el modelmap para mostrarlos en la vista
			modelmap.addAttribute("reviews", reviews);
			modelmap.addAttribute("bookings", bookings);
			modelmap.addAttribute("hotel", hotel);
			
			// Manda todos los atributos a la vista listaReservas.jsp
			
			log.info("Se muestran todas las reservas, hoteles y reviews");
			return "hotel/listaReservas";

		} else {
			log.info("No se muestran hoteles porque no hay ninguno en la bd");
			modelmap.addAttribute("message", "No hay hoteles disponibles en este momento");
			return "welcome";
		}

	}

	// LISTADO DE TODOS LOS HOTELES
	@GetMapping(path = "/listadoHoteles")
	public String listadoHoteles(ModelMap modelmap) {

		// Trae todas las reservas y reseñas
		List<Hotel> hoteles = (List<Hotel>) hotelService.findAll();
		List<Integer> numBookings= new ArrayList<Integer>();
		List<Integer> numReviews= new ArrayList<Integer>();

		if (hoteles.iterator().hasNext()) {
			
			for (int i = 0; i < hoteles.size(); i++) {
				
				numBookings.add(bookingService.findBookingsByHotelId(hoteles.get(i).getId()).size())  ;
				numReviews.add(reviewService.findReviewByHotelId(hoteles.get(i).getId()).size())  ;
				}
			
			modelmap.addAttribute("hoteles", hoteles);
			modelmap.addAttribute("numBookings", numBookings);
			modelmap.addAttribute("numReviews", numReviews);
			log.info("Se muestran todos los hoteles");
			return "hotel/listadoHoteles";

		} else {
			modelmap.addAttribute("message",
					"No hay hoteles disponibles en este momento, crea uno para acceder al listado");
			return crearHotel(modelmap);
		}

	}

	// LISTA DE MIS RESERVAS
	@GetMapping(path = "/myBookings")
	public String listadoMisReservas(ModelMap modelmap) {
		if (ownerService.esOwner()) { // si es owner, muestra tus reservas, si no, vuelve a todas las reservas

			// Obtiene el id del owner para redirigir a la vista de reservas de ese owner
			devolverOwner(modelmap);
			log.info("Se muestran todas las reservas del owner ");
			return listadoReservasPorOwner((int) modelmap.getAttribute("ownerId"), modelmap);

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
		modelmap.addAttribute("ownerId", ownerId);
		log.info("Se muestran todas las reservas del owner: "+ownerId);
		// Redirige a misReservas.jsp
		return "hotel/misReservas";

	}

	// CREAR UN NUEVO HOTEL
		@GetMapping(path = "/new")
		public String crearHotel(ModelMap modelmap) {

			
			Hotel hotel = new Hotel();
			

			modelmap.addAttribute("hotel", hotel);

			// Redirige al formulario editBooking.jsp
			log.info("Se muestra el formulario de crear hotel");
			return "hotel/newHotel";

		}

		@PostMapping(path = "/new")
		public String guardarBooking(@Valid Hotel hotel, BindingResult result,ModelMap modelmap) {
			
			
			if (result.hasErrors()) {			
				//modelmap.addAttribute("message", "No se ha podido crear el hotel");
				return "hotel/newHotel";

			}

			else {
			
			modelmap.addAttribute("message", "Hotel creado con éxito!");
			hotelService.save(hotel);
			
			String vista = listadoHoteles(modelmap);
			log.info("Se crea un nuevo hotel en :"+hotel.getCity()+" con aforo de "+hotel.getAforo());
			return vista;
			}
		}


	// BORRAR UN HOTEL
	@GetMapping(path = "/delete/{hotelId}")
	public String borrarHotel(@PathVariable("hotelId") Integer hotelId, ModelMap modelmap) {

		
		// Llegados a este punto, para el hotel seleccionado se han borrado todas las
		// reviews y bookings, por lo que procedemos a borrarlo
		hotelService.deleteById(hotelId);
		modelmap.addAttribute("message", "Hotel borrado con éxito!");
		log.info("Se borra de la bd el hotel "+hotelId);
		return listadoHoteles(modelmap);
	}

}
