package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
	public ReviewController(OwnerService ownerService, ReviewService reviewService, HotelService hotelService) {

		this.ownerService = ownerService;
		this.reviewService = reviewService;
		this.hotelService = hotelService;

	}

	@InitBinder("review")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ReviewValidator());
	}

	// nueva reseña al hotel
	@GetMapping()
	public String crearReviewHotel(ModelMap modelmap) {

		if (ownerService.esOwner()) {

			// Manda una review vacia al formulario junto al owner, para que se rellene y se
			// mande al metodo "/save"

			hotelController.devolverOwner(modelmap);

			Review review = new Review();
			List<Hotel> hoteles = (List<Hotel>) hotelService.findAll();
			modelmap.addAttribute("review", review);
			modelmap.addAttribute("hoteles", hoteles);
			return "reviews/newReview";

		} else {
			modelmap.clear();
			modelmap.addAttribute("message", "Solo los owners pueden hacer reviews del hotel");
			return hotelController.listadoReservas(modelmap);
		}

	}

	// nueva reseña al hotel
	@PostMapping(path = "saveReview/{ownerId}")
	public String guardarReview(@Valid Review review, BindingResult result, ModelMap modelmap,
			@PathVariable("ownerId") Integer ownerId) { // pathparam coge el parametro del formulario hidden
		if (result.hasErrors()) {
			modelmap.addAttribute("review", review);
			modelmap.addAttribute("message",
					result.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList()));

			return crearReviewHotel(modelmap);

		} else {
			review.setOwner(ownerService.findOwnerById(ownerId));
			Integer ownerActual = ownerService.devolverOwnerId();
			modelmap.addAttribute("ownerId", ownerActual);
			// Obtiene la reseña del formulario y la guarda en la bd

			if (reviewService.puedeReseñar(review, ownerId)) {
				reviewService.save(review);

				modelmap.addAttribute("message",
						"Review creada con éxito en el hotel de " + review.getHotel().getCity());

				// Cuando acaba, redirecciona a la lista de reservas, donde esta la review
				return hotelController.listadoReservas(modelmap);
			} else {
				modelmap.addAttribute("message",
						"No puedes crear otra reserva para el hotel de " + review.getHotel().getCity());
				return crearReviewHotel(modelmap);
			}

		}
	}

	// BORRAR UNA RESERVA
	@GetMapping(path = "/delete/{reviewId}")
	public String borrarReview(@PathVariable("reviewId") Integer reviewId, ModelMap modelmap) {

		if (ownerService.esOwner()) {
			Integer ownerActual = ownerService.devolverOwnerId();

			Review review = reviewService.findReviewById(reviewId).get();
			// Si la review está la borra, si no, te redirecciona a la lista de reviews
			if ((ownerService.devolverOwnerId().equals(review.getOwner().getId()))) {

				reviewService.deleteById(reviewId);

				modelmap.addAttribute("message", "Review borrada con éxito!! ");

			} else {
				modelmap.addAttribute("message", "No puedes borrar reviews de otros owners");
			}

			// Cuando acaba el metodo, te redirecciona a la lista de reservas del owner

		} else if (ownerService.esAdmin()) {
			reviewService.deleteById(reviewId);
			modelmap.addAttribute("message", "Booking borrado con éxito!");

		} else {
			modelmap.addAttribute("message", "No puedes borrar reviews de otros owners");
		}

		return hotelController.listadoReservas(modelmap);
	}

}
