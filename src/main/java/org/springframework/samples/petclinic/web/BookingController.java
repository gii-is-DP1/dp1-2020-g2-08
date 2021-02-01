package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotel/booking/")
public class BookingController {

	@Autowired
	private HotelController hotelController;

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private HotelService hotelService;

	@Autowired
	public BookingController(OwnerService ownerService, BookingService bookingService,
			HotelService hotelService) {

		this.ownerService = ownerService;
		this.bookingService = bookingService;
		this.hotelService = hotelService;
	}

	@ModelAttribute("hoteles")
	public Iterable<Hotel> findHotels() {
		return this.hotelService.findAll();
	}

	@InitBinder("booking")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new BookingValidator());
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(path = "/new")
	public String elegirHotel(ModelMap modelmap) {
		List<Hotel> hoteles = (List<Hotel>) hotelService.findAll();
		List<Integer> bookings = new ArrayList<Integer>();
		List<Integer> reviews = new ArrayList<Integer>();
		List<Integer> valoracionMedia = new ArrayList<Integer>();
		int cont = 0;
		for (int i = 0; i < hoteles.size(); i++) {
			bookings.add(hoteles.get(i).getBookings().size());
			reviews.add(hoteles.get(i).getReviews().size());
			Set<Review> reviewss = hoteles.get(i).getReviews();
			List<Review> rev = reviewss.stream().collect(Collectors.toList());
			for (int j = 0; j < rev.size(); j++) {
				cont = cont + rev.get(j).getStars();

			}
			if (reviewss.size() < 1) {
				valoracionMedia.add(0);
				cont = 0;
			} else {
				valoracionMedia.add(cont / reviewss.size());
				cont = 0;

			}
		}

		modelmap.addAttribute("hoteles", hoteles);
		modelmap.addAttribute("numeroBookings", bookings);
		modelmap.addAttribute("numeroReviews", reviews);
		modelmap.addAttribute("valoracionMedia", valoracionMedia);
		return "hotel/newBooking";
	}

	// CREAR UNA NUEVA RESERVA
	@GetMapping(path = "/new/{hotelId}")
	public String crearBooking(ModelMap modelmap, @PathVariable("hotelId") Integer hotelId) {
		// Comprueba que hay hoteles creados
		if (hotelService.findAll().iterator().hasNext()) {
			// comprueba que es un owner quien va a hacer el booking
			if (ownerService.esOwner()) {
				Hotel hotel = hotelService.findById(hotelId);
				Integer ownerId = ownerService.devolverOwnerId();
				Owner owner = ownerService.findOwnerById(ownerId);
				List<Pet> pets = new ArrayList<Pet>();
				pets = ownerService.findOwnerById(ownerId).getPets();calendario(modelmap, hotelId);
				
				// Crea una lista con las mascotas para que las muestre en el formulario de
				// nueva reserva
				
				

				Booking booking = new Booking();
				creaModelMap(hotel, owner, ownerId, pets, booking, modelmap);

				// Redirige al formulario editBooking.jsp
				return "hotel/editBooking";
			} else {
				modelmap.put("message", "Para poder crear una reserva, tienes que estar logueado como owner");
				return hotelController.listadoReservas(modelmap);
			}
		} else {
			modelmap.put("message", "No puedes crear una nueva reserva hasta que no haya un hotel disponible");
			return "welcome";
		}

	}

	// CREAR UNA NUEVA RESERVA
	@PostMapping(path = "/new/{hotelId}")
	public String guardarBooking(@Valid Booking booking, BindingResult result, @PathParam("ownerId") Integer ownerId,
			@PathVariable("hotelId") Integer hotelId, ModelMap modelmap) {
		
		calendario(modelmap, hotelId);
		Hotel hotel = hotelService.findById(hotelId);
		Owner owner = ownerService.findOwnerById(ownerId);
		List<Pet> pets = new ArrayList<Pet>();
		pets = ownerService.findOwnerById(ownerId).getPets();
		
		if (result.hasErrors()) {

			creaModelMap(hotel, owner, ownerId, pets, booking, modelmap);
			modelmap.put("message", "Hubo un error al crear el booking");
			return "hotel/editBooking";

		} else {
			
			return validaBooking(modelmap, booking, ownerId, hotelId);

		}

	}

	// EDITAR UNA RESERVA

	@GetMapping(path = "/edit/{bookingId}")
	public String edit(@PathVariable("bookingId") int bookingId, ModelMap modelmap) {
		Integer ownerId = ownerService.devolverOwnerId();
		Booking booking = bookingService.findBookingById(bookingId);
		Hotel hotel = booking.getHotel();
		calendario(modelmap, booking.getHotel().getId());
		// si la reserva es del owner, deja editarla, si no, te manda a las reservas
		if (ownerId != null && (bookingService.findBookingById(bookingId).getOwner().getId().equals(ownerId))) {
			List<Pet> pets = ownerService.findOwnerById(ownerId).getPets();

			Owner owner = ownerService.findOwnerById(ownerId);
			creaModelMap(hotel, owner, ownerId, pets, booking, modelmap);
			return "hotel/editBooking";
		} else {
			modelmap.addAttribute("message", "No puedes editar bookings de otro owner");
			return hotelController.listadoReservas(modelmap);
		}

	}

	@PostMapping(value = "/edit/{bookingId}")
	public String processUpdateForm(@Valid Booking booking, BindingResult result,
			@PathVariable("bookingId") int bookingId, ModelMap modelmap) {
		Integer ownerId = ownerService.devolverOwnerId();
		List<Pet> pets = ownerService.findOwnerById(ownerId).getPets();
		Owner owner = ownerService.findOwnerById(ownerId);
		Hotel hotel = booking.getHotel();
		if (result.hasErrors()) {
			calendario(modelmap, booking.getHotel().getId()); // añade las restricciones de dias al calendario
			creaModelMap(hotel, owner, ownerId, pets, booking, modelmap);
			modelmap.put("message", "Hubo un error al crear el booking");
			return "hotel/editBooking";
		} else {
			if (bookingService.estaOcupadoEnRango(booking.getStartDate(), booking.getEndDate(),
					booking.getHotel().getId())) {
				calendario(modelmap, hotel.getId());
				creaModelMap(hotel, owner, ownerId, pets, booking, modelmap);
				modelmap.addAttribute("message", "La reserva contiene días que ya se ha alcanzado el aforo máximo");
				return "hotel/editBooking";

			} else {
				Booking bookingToUpdate = this.bookingService.findBookingById(bookingId);
				BeanUtils.copyProperties(booking, bookingToUpdate, "id", "owner", "pet", "hotel");

				this.bookingService.save(bookingToUpdate);
				modelmap.addAttribute("message", "La reserva se ha editado correctamente");
				return hotelController.listadoMisReservas(modelmap);
			}
		}
	}

	// BORRAR UNA RESERVA
	@GetMapping(path = "/delete/{bookingId}/{ownerId}")
	public String borrarBooking(@PathVariable("bookingId") Integer bookingId, @PathVariable("ownerId") Integer ownerId,
			ModelMap modelmap) {

		if (ownerService.esOwner()) {
			// Si la reserva está la borra, si no, te redirecciona a la lista de reservas
			if ((ownerService.devolverOwnerId().equals(ownerId))) {

				bookingService.delete(bookingService.findBookingById(bookingId));
				modelmap.addAttribute("message", "Booking borrado con éxito!");

			} else {
				modelmap.addAttribute("message", "No puedes borrar bookings de otro owner");
			}

			// Cuando acaba el metodo, te redirecciona a la lista de reservas del owner

		} else if (ownerService.esAdmin()) {
			bookingService.deleteById(bookingId);
			modelmap.addAttribute("message", "Booking borrado con éxito!");
			return hotelController.listadoReservas(modelmap);
		}

		return hotelController.listadoReservasPorOwner(ownerId, modelmap);
	}

	/**
	 * Valida que el numero de booking por owner sea menor o igual a 3, que una
	 * mascota npo tenga mas de 1 reserva, y que la reserva que se va a hacer no
	 * incluya dias en los que el hotel está lleno.En caso de que una no se cumpla,
	 * te devuelve al formulario de creacion
	 * 
	 * @param modelmap modelmap en el que introduce los datos para la vista * @param
	 *                 booking booking creado al que se le va a hacer la validacion
	 *                 * @param ownerId owner que quiere hacer el booking
	 * @param hotelId  hotel que queremos ver las restricciones de fechas
	 */
	public String validaBooking(ModelMap modelmap, @Valid Booking booking, Integer ownerId, Integer hotelId) {

		if (bookingService.numeroBookingsPorOwner(ownerId) >= 3) {
			modelmap.addAttribute("message", "No se pueden hacer más de 3 reservas");
			return crearBooking(modelmap, hotelId);
		} else if (bookingService.numeroBookingsPorPet(booking.getPet().getId()) >= 1) {
			modelmap.addAttribute("message", "Esta mascota ya tiene una reserva en un hotel");
			return crearBooking(modelmap, hotelId);
		}

		else {

			if (bookingService.estaOcupadoEnRango(booking.getStartDate(), booking.getEndDate(),
					booking.getHotel().getId())) {
				modelmap.addAttribute("message", "La reserva contiene días que ya se ha alcanzado el aforo máximo");
				return crearBooking(modelmap, hotelId);

			}

			else {
				booking.setHotel(hotelService.findById(hotelId));
				booking.setOwner(ownerService.findOwnerById(ownerId));
				bookingService.save(booking);
				modelmap.addAttribute("message", "Booking creado con éxito!");
				String vista = hotelController.listadoReservasPorOwner(ownerId, modelmap);
				return vista;
			}
		}

	}

	/**
	 * Añade las restricciones al calendario de la vista jsp, además, pasa los dias
	 * para que se muestren como texto, diciendo los dias llenos de el hotel
	 * 
	 * @param modelmap modelmap en el que introduce los datos para la vista
	 * @param hotelId  hotel que queremos ver las restricciones de fechas
	 */
	public void calendario(ModelMap modelmap, int hotelId) {
		if (bookingService.findBookingsByHotelId(hotelId).size() > 4) {
			List<LocalDate> res = bookingService.diasOcupados(4, hotelId);// hay que cambiar el 4 por el aforo
			List<String> res2 = bookingService.diasOcupadosStr(4, hotelId); // hay que cambiar el 4 por el aforo
			modelmap.addAttribute("restriccion", bookingService.restriccionCalendario(res2));
			modelmap.put("diasOcupados", bookingService.parseaDates2(res));
		} else {
			modelmap.addAttribute("restriccion", "new Date().getDate() == date.getDate()");
		}
	}

	public void creaModelMap(Hotel hotel, Owner owner, int ownerId, List<Pet> pets, Booking booking,
			ModelMap modelmap) {
		modelmap.put("hotel", hotel);
		modelmap.addAttribute("owner", owner);
		modelmap.addAttribute("ownerId", ownerId);
		modelmap.put("hotelId", hotel.getId());

		modelmap.put("pets", pets);
		modelmap.put("booking", booking);
	}

}