package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ReviewService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotel/booking/")
public class BookingController {

	@Autowired
	HotelController hotelController;

	@Autowired
	private OwnerService ownerService;
	@Autowired
	private PetService petService;
	@Autowired
	private BookingService bookingService;
	@Autowired
	HotelService hotelService;

	@Autowired
	public BookingController(PetService petService, OwnerService ownerService, BookingService bookingService,
			HotelService hotelService) {

		this.ownerService = ownerService;
		this.petService = petService;
		this.bookingService = bookingService;
		this.hotelService = hotelService;

	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	// CREAR UNA NUEVA RESERVA
	@GetMapping(path = "/new")
	public String crearBooking(ModelMap modelmap) {
		if (hotelService.findAll().iterator().hasNext()) {
			if (ownerService.esOwner()) {
				Integer ownerId = ownerService.devolverOwnerId();

				// Crea una lista con las mascotas para que las muestre en el formulario de
				// nueva reserva
				List<Pet> pets = new ArrayList<Pet>();
				pets = ownerService.findOwnerById(ownerId).getPets();

				Owner owner = ownerService.findOwnerById(ownerId);

				// Tambien obtiene el id de la url, para poder crear una reserva con ese owner
				// que recibe
				Booking booking = new Booking();
				List<Hotel> hoteles = (List<Hotel>) hotelService.findAll();
				modelmap.addAttribute("booking", booking);
				modelmap.addAttribute("owner", owner);
				modelmap.addAttribute("ownerId", ownerId);
				modelmap.addAttribute("pets", pets);
				modelmap.addAttribute("hoteles", hoteles);

				// Redirige al formulario editBooking.jsp
				return "hotel/editBooking";
			} else {
				modelmap.addAttribute("message", "Para poder crear una reserva, tienes que estar logueado como owner");
				return hotelController.listadoReservas(modelmap);
			}
		} else {
			modelmap.addAttribute("message", "No puedes crear una nueva reserva hasta que no haya un hotel disponible");
			return "welcome";
		}

	}

	// CREAR UNA NUEVA RESERVA
	@PostMapping(path = "/save/{ownerId}")
	public String guardarBooking(@Valid Booking booking, @PathVariable("ownerId") Integer ownerId, ModelMap modelmap) {

		Owner o = ownerService.findOwnerById(ownerId);
		booking.setOwner(o);
		if (bookingService.fechaValida(booking) && bookingService.numeroDiasValido(booking)) {
			if (bookingService.numeroBookingsPorOwner(ownerId)>=3) {
				modelmap.addAttribute("message", "No se pueden hacer más de 3 reservas");
				return crearBooking(modelmap);
			}
			else if (bookingService.numeroBookingsPorPet(booking.getPet().getId())>=1) {
				modelmap.addAttribute("message", "Esta mascota ya tiene una reserva en un hotel");
				return crearBooking(modelmap);
			}
			else {bookingService.save(booking);
			modelmap.clear();
			modelmap.addAttribute("message", "Booking creado con éxito!");

			
			String vista = hotelController.listadoReservasPorOwner(ownerId, modelmap);
			return vista;
				
			}
			
		} else {
			modelmap.addAttribute("message", "La reserva tiene que ser de mas de 1 dia y como maximo de 7");
			return crearBooking(modelmap);
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
 
	// EDITAR UNA RESERVA

	@GetMapping(path = "/edit/{bookingId}")
	public String edit(@PathVariable("bookingId") int bookingId, ModelMap modelmap) {
		Integer ownerId = ownerService.devolverOwnerId();
		// si la reserva es del owner, deja editarla, si no, te manda a las reservas
		if (bookingService.findBookingById(bookingId).getOwner().getId().equals(ownerId)) {

			List<Hotel> hoteles = (List<Hotel>) hotelService.findAll();

			List<Pet> pets = new ArrayList<Pet>();
			pets = ownerService.findOwnerById(ownerId).getPets();
			Booking booking = this.bookingService.findBookingById(bookingId);
			Owner owner = ownerService.findOwnerById(ownerId);
			modelmap.addAttribute("owner", owner);
			modelmap.addAttribute("ownerId", ownerId);
			modelmap.addAttribute("pets", pets);
			modelmap.addAttribute("hoteles", hoteles);

			modelmap.put("booking", booking);
			return "hotel/editBooking2";
		}
		else {
			
			return hotelController.listadoReservas(modelmap);
		}

	}

	@PostMapping(value = "/edit/{bookingId}")
	public String processUpdateForm(@Valid Booking booking, BindingResult result,
			@PathVariable("bookingId") int bookingId, ModelMap modelmap) throws DuplicatedPetNameException {
		if (result.hasErrors()) {
			modelmap.put("booking", booking);
			modelmap.put("message", "Hubo un error al editar el booking");
			return "hotel/editBooking2";
		} else {
			Booking bookingToUpdate = this.bookingService.findBookingById(bookingId);

			bookingToUpdate.setStartDate(booking.getStartDate());
			bookingToUpdate.setEndDate(booking.getEndDate());
			bookingToUpdate.setHotel(booking.getHotel());
			bookingToUpdate.setOwner(booking.getOwner());
			bookingToUpdate.setPet(booking.getPet());
			if (bookingService.fechaValida(bookingToUpdate) && bookingService.numeroDiasValido(bookingToUpdate)) {
			this.bookingService.save(bookingToUpdate);
			modelmap.addAttribute("message", "La reserva se ha editado correctamente");
			return hotelController.listadoMisReservas(modelmap);}
			else {
				modelmap.addAttribute("message", "La reserva tiene que ser de mas de 1 dia y como maximo de 7");
				return edit(bookingId, modelmap);
			}
		}
	}

}