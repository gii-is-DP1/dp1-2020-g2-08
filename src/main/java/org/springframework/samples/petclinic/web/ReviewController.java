package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Review;
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
	HotelController hotelController;
	@Autowired
	private OwnerService ownerService;
	

	@Autowired
	private ReviewService reviewService;

	@Autowired
	public ReviewController(  OwnerService ownerService,
			 ReviewService reviewService) {

		this.ownerService = ownerService;
		this.reviewService = reviewService;

	}
	
	// nueva reseña al hotel
		@GetMapping()
		public String crearReviewHotel(ModelMap modelmap) {

			String vista = hotelController.listadoReservas(modelmap);

			if (ownerService.esOwner()) {

				// Manda una review vacia al formulario junto al owner, para que se rellene y se
				// mande al metodo "/save"

				hotelController.devolverOwner(modelmap);
				modelmap.addAttribute("review", new Review());
				vista = "reviews/newReview";

			} else {
				modelmap.clear();
				modelmap.addAttribute("message", "Solo los owners pueden hacer reviews del hotel");
				vista = hotelController.listadoReservas(modelmap);
			}
			return vista;

		}

		// nueva reseña al hotel
		@PostMapping(path = "review/saveReview/{ownerName}")
		public String guardarReview(@Valid Review review, BindingResult result, ModelMap modelmap,
				@PathVariable("ownerName") String ownerName) {

			review.setOwnerName(ownerName);
			// Obtiene la reseña del formulario y la guarda en la bd
			reviewService.save(review);
			modelmap.addAttribute("message", "Review creada con éxito!");

			// Cuando acaba, redirecciona a la lista de reservas, donde esta la review
			return hotelController.listadoReservas(modelmap);

		}
}
