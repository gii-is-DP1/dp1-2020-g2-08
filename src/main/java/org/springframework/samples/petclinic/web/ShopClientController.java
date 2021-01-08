package org.springframework.samples.petclinic.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class ShopClientController {

	@Autowired
	private ProductService productService;


	private static List<String> categoryList = Arrays.asList("Pets", "Food", "Toys", "Accessories");
	private static List<String> offerOptions = Arrays.asList("Yes", "No");

	public ShopClientController(ProductService productService) {

		this.productService = productService;
	}

	@GetMapping()
	public String productList(ModelMap modelmap) {
		String view = "shop/home";
		Iterable<Product> products = productService.findAll();
		int productsNumber = productService.productCount();

		modelmap.addAttribute("product", products);
		modelmap.addAttribute("productsNumber", productsNumber);

		return view;

	}

	@GetMapping(path = "/products/{category}") // {category}
	public String petList(@PathVariable (value="category") String category, ModelMap modelmap) {
		String view = "shop/products/productByCategory";
		List<Product> products = (List<Product>) productService.findAll();
		List<Product> productsByCategory = products.stream().filter(x -> x.getCategory().equals(category))
				.collect(Collectors.toList());
		int productsNumber = productsByCategory.size();

		modelmap.addAttribute("product", productsByCategory);
		modelmap.addAttribute("productsNumber", productsNumber);
		modelmap.addAttribute("category", category);

		return view;

	}

	
	@GetMapping(path = "/products/review/{productId}")
	public String productReview(ModelMap modelmap, @PathVariable (value="productId") Integer productId) {
			Review review = new Review();
			List<Product> products = (List<Product>) productService.findAll();
			modelmap.addAttribute("review", review);
			modelmap.addAttribute("product", products);
			return "reviews/newProductReview";

		

	}

//	// nueva reseña al hotel
//	@PostMapping(path = "saveReview/{ownerId}")
//	public String guardarReview(@Valid Review review, BindingResult result, ModelMap modelmap,
//			@PathVariable("ownerId") Integer ownerId) { // pathparam coge el parametro del formulario hidden
//		if (result.hasErrors()) {
//			modelmap.addAttribute("review", review);
//			modelmap.addAttribute("message",
//					result.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList()));
//
//			return crearReviewHotel(modelmap);
//
//		} else {
//			review.setOwner(ownerService.findOwnerById(ownerId));
//			Integer ownerActual = ownerService.devolverOwnerId();
//			modelmap.addAttribute("ownerId", ownerActual);
//			// Obtiene la reseña del formulario y la guarda en la bd
//
//			if (reviewService.puedeReseñar(review, ownerId)) {
//				reviewService.save(review);
//
//				modelmap.addAttribute("message",
//						"Review creada con éxito en el hotel de " + review.getHotel().getCity());
//
//				// Cuando acaba, redirecciona a la lista de reservas, donde esta la review
//				return hotelController.listadoReservas(modelmap);
//			} else {
//				modelmap.addAttribute("message",
//						"No puedes crear otra reserva para el hotel de " + review.getHotel().getCity());
//				return crearReviewHotel(modelmap);
//			}
//
//		}
//	}

}
