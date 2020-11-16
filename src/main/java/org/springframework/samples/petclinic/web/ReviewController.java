package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("hotel/review")
public class ReviewController {
	
	@Autowired
	private HotelController hotelController;
	@Autowired
	private OwnerService ownerService;
	@Autowired
	private HotelService hotelService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	public ReviewController(  OwnerService ownerService,
			 ReviewService reviewService,HotelService hotelService) {

		this.ownerService = ownerService;
		this.reviewService = reviewService;
		this.hotelService=hotelService;

	}
	
	// nueva reseña al hotel
		@GetMapping()
		public String crearReviewHotel(ModelMap modelmap) {

			String vista = hotelController.listadoReservas(modelmap);

			if (ownerService.esOwner()) {
		
				// Manda una review vacia al formulario junto al owner, para que se rellene y se
				// mande al metodo "/save"

				hotelController.devolverOwner(modelmap);
		
				Review review = new Review();
				List<Hotel> hoteles = (List<Hotel>) hotelService.findAll();
				modelmap.addAttribute("review", review);
				modelmap.addAttribute("hoteles", hoteles);
				vista = "reviews/newReview";

			} else {
				modelmap.clear();
				modelmap.addAttribute("message", "Solo los owners pueden hacer reviews del hotel");
				vista = hotelController.listadoReservas(modelmap);
			}
			return vista;

		}

		// nueva reseña al hotel
		@PostMapping(path = "saveReview/{ownerId}")
		public String guardarReview(@Valid Review review, BindingResult result, ModelMap modelmap,
				@PathVariable("ownerId") Integer ownerId ) { // pathparam coge el parametro del formulario hidden
			
//			review.setHotel(hotelService.findById(hotelId));
			review.setOwner(ownerService.findOwnerById(ownerId));
			Integer ownerActual=ownerService.devolverOwnerId();
			modelmap.addAttribute("ownerId", ownerActual);
			// Obtiene la reseña del formulario y la guarda en la bd
			reviewService.save(review);
		
			
			modelmap.addAttribute("message", "Review creada con éxito en el hotel 1");

			// Cuando acaba, redirecciona a la lista de reservas, donde esta la review
			return hotelController.listadoReservas(modelmap);

		}
		
		
		// BORRAR UNA RESERVA
				@GetMapping(path = "/delete/{reviewId}")
				public String borrarReview(@PathVariable("reviewId") Integer reviewId, @PathParam("ownerId") Integer ownerId,
						ModelMap modelmap) {
					
					

					if (ownerService.esOwner()) {
						Integer ownerActual=ownerService.devolverOwnerId();
						// Si la review está la borra, si no, te redirecciona a la lista de reviews
						if ( (ownerService.devolverOwnerId().equals(ownerId))) {
							
							reviewService.deleteById(reviewId);
							
							modelmap.addAttribute("message", "Review borrada con éxito!! ");

						} else {
							modelmap.addAttribute("message", "No puedes borrar reviews de otros owners "+modelmap.getAttribute("ownerId")+" asdcsdc");
						}

						// Cuando acaba el metodo, te redirecciona a la lista de reservas del owner
						
					}
					else if ( ownerService.esAdmin() ) {
						reviewService.deleteById(reviewId);
						modelmap.addAttribute("message", "Booking borrado con éxito!");
						
					}
					else {
						modelmap.addAttribute("message", "No puedes borrar reviews de otros owners");
					}
						
					return hotelController.listadoReservas(modelmap);
		}
		
		
		
		
}
