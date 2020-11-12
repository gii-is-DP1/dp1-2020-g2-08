package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
	public BookingController( PetService petService, OwnerService ownerService,
			BookingService bookingService) {
		
		this.ownerService = ownerService;
		this.petService = petService;
		this.bookingService = bookingService;
		

	}
	
	// CREAR UNA NUEVA RESERVA
		@GetMapping(path = "/new")
		public String crearBooking( ModelMap modelmap) {
			if (ownerService.esOwner()) {
		Integer ownerId=ownerService.devolverOwnerId();
		
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
	else {
		modelmap.addAttribute("message", "Para poder crear una reserva, tienes que estar logueado como owner");
		return "welcome";
	}	

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
			String vista = hotelController.listadoReservasPorOwner(ownerId, modelmap);
			return vista;

		}

		// BORRAR UNA RESERVA
		@GetMapping(path = "/delete/{bookingId}/{ownerId}")
		public String borrarBooking(@PathVariable("bookingId") int bookingId, @PathVariable("ownerId") int ownerId,
				ModelMap modelmap) {
			Optional<Booking> booking = bookingService.findBookingById(bookingId);

			if (ownerService.esOwner()) {
				// Si la reserva está la borra, si no, te redirecciona a la lista de reservas
				if (booking.isPresent() && (ownerService.devolverOwnerId().equals(ownerId))) {
					
					bookingService.delete(booking.get());
					modelmap.addAttribute("message", "Booking borrado con éxito!");

				} else {
					modelmap.addAttribute("message", "No puedes borrar bookings de otro owner");
				}

				// Cuando acaba el metodo, te redirecciona a la lista de reservas del owner
				
			}
			else if ( ownerService.esAdmin() ) {
				bookingService.delete(booking.get());
				modelmap.addAttribute("message", "Booking borrado con éxito!");
				
			}
				
			return hotelController.listadoReservasPorOwner(ownerId, modelmap);
}}