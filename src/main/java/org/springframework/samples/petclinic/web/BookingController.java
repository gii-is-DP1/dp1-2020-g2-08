package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
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
		List<Integer> valoracionMedia= new ArrayList<Integer>();
		int cont = 0;
		for (int i = 0; i < hoteles.size(); i++) {
			bookings.add(hoteles.get(i).getBookings().size());
			reviews.add(hoteles.get(i).getReviews().size());
			Set<Review> reviewss = hoteles.get(i).getReviews();
			List<Review> rev = reviewss.stream().collect(Collectors.toList());
			for (int j = 0; j < rev.size(); j++) {
			cont=cont+	rev.get(j).getStars();
			
			}
			valoracionMedia.add(cont/reviewss.size());
			cont=0;
			
			
		}
		
		modelmap.addAttribute("hoteles",hoteles);
		modelmap.addAttribute("numeroBookings",bookings);
		modelmap.addAttribute("numeroReviews",reviews);
		modelmap.addAttribute("valoracionMedia",valoracionMedia);
		return "hotel/newBooking";
	}
	
	
	
	// CREAR UNA NUEVA RESERVA
	@GetMapping(path = "/new/{hotelId}")
	public String crearBooking(ModelMap modelmap, @PathVariable("hotelId") Integer hotelId) {
		//Comprueba que hay hoteles creados
		if (hotelService.findAll().iterator().hasNext()) {
			//comprueba que es un owner quien va a hacer el booking
			if (ownerService.esOwner()) {
				if (bookingService.findBookingsByHotelId(hotelId).size()>4) { 
					List<LocalDate> res = bookingService.diasOcupados(4,hotelId);// hay que cambiar el 4 por el aforo
					List<String> res2 = bookingService.diasOcupadosStr(4,hotelId);	// hay que cambiar el 4 por el aforo
				modelmap.addAttribute("restriccion", bookingService.restriccionCalendario(res2));
				modelmap.put("diasOcupados", bookingService.parseaDates2(res)); 
				}
				else {
					modelmap.addAttribute("restriccion","new Date().getDate() == date.getDate()");
				}
				Integer ownerId = ownerService.devolverOwnerId();
				Owner owner = ownerService.findOwnerById(ownerId);
				// Crea una lista con las mascotas para que las muestre en el formulario de
				// nueva reserva
				List<Pet> pets = new ArrayList<Pet>();
				pets = ownerService.findOwnerById(ownerId).getPets();
				Hotel hotel = hotelService.findById(hotelId);
				
				Booking booking = new Booking();
				modelmap.put("hotel", hotel);
				modelmap.put("booking", booking);
				modelmap.put("owner", owner);
				modelmap.put("ownerId", ownerId);
				modelmap.put("pets", pets);
				
				

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
	public String guardarBooking(@Valid Booking booking, BindingResult result, @PathParam("ownerId") Integer ownerId,@PathVariable("hotelId") Integer hotelId,
			ModelMap modelmap) {
		if (bookingService.findBookingsByHotelId(hotelId).size()>4) { 		// hay que cambiar el 4 por el aforo
			List<String> res2 = bookingService.diasOcupadosStr(4,hotelId);	// hay que cambiar el 4 por el aforo
		modelmap.addAttribute("restriccion", bookingService.restriccionCalendario(res2));
		}
		else {
			modelmap.addAttribute("restriccion","new Date().getDate() == date.getDate()");
		}
		
		if (result.hasErrors()) {
			Owner owner = ownerService.findOwnerById(ownerId);
			List<Pet> pets = new ArrayList<Pet>();
			pets = ownerService.findOwnerById(ownerId).getPets();
			
			modelmap.put("message", "Hubo un error al crear el booking");			
			modelmap.put("booking", booking);
			modelmap.put("owner", owner);
			modelmap.put("pets", pets);
			modelmap.put("hotelId", hotelId);
			modelmap.put("hotel", hotelService.findById(hotelId));
			return "hotel/editBooking";
		} else {
		return	validaBooking(modelmap, booking,ownerId,hotelId);
					
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
		List<String> res2 = bookingService.diasOcupadosStr(4,1);
		modelmap.addAttribute("restriccion", bookingService.restriccionCalendario(res2));
		// si la reserva es del owner, deja editarla, si no, te manda a las reservas
		if (ownerId != null && (bookingService.findBookingById(bookingId).getOwner().getId().equals(ownerId))) {

			
			List<Pet> pets = ownerService.findOwnerById(ownerId).getPets();
			Booking booking = this.bookingService.findBookingById(bookingId);
			Owner owner = ownerService.findOwnerById(ownerId);
			Hotel hotel = booking.getHotel();
			modelmap.put("hotel", hotel);
			modelmap.addAttribute("owner", owner);
			modelmap.addAttribute("ownerId", ownerId);
			modelmap.addAttribute("pets", pets);
			modelmap.put("booking", booking);
			
			return "hotel/editBooking";
		} else {
			modelmap.addAttribute("message", "No puedes editar bookings de otro owner");
			return hotelController.listadoReservas(modelmap);
		}

	}

	@PostMapping(value = "/edit/{bookingId}")
	public String processUpdateForm(@Valid Booking booking, BindingResult result,
			@PathVariable("bookingId") int bookingId, ModelMap modelmap)  {
		if (result.hasErrors()) {
			
		List<Pet> pets = ownerService.findOwnerById(booking.getOwner().getId()).getPets();
		
		modelmap.put("message", "Hubo un error al crear el booking");
		modelmap.put("pets", pets);		
			modelmap.put("booking", booking);
//			modelmap.put("message",result.getAllErrors());
			return "hotel/editBooking";
		} else {
			Booking bookingToUpdate = this.bookingService.findBookingById(bookingId);
			BeanUtils.copyProperties(booking, bookingToUpdate, "id","owner","pet","hotel");   
//			bookingToUpdate.setStartDate(booking.getStartDate());
//			bookingToUpdate.setEndDate(booking.getEndDate());
			

			
			
				this.bookingService.save(bookingToUpdate);
				modelmap.addAttribute("message", "La reserva se ha editado correctamente");
				return hotelController.listadoMisReservas(modelmap); 
			
		}
	}

	public String validaBooking(ModelMap modelmap, @Valid Booking booking, Integer ownerId, Integer hotelId ) {
		
		
		if (bookingService.numeroBookingsPorOwner(ownerId) >= 3) {
			modelmap.addAttribute("message", "No se pueden hacer más de 3 reservas");
			return crearBooking(modelmap, hotelId);
		} else if (bookingService.numeroBookingsPorPet(booking.getPet().getId()) >= 1) {
			modelmap.addAttribute("message", "Esta mascota ya tiene una reserva en un hotel");
			return crearBooking(modelmap, hotelId);}
		
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
}