package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.ProductReview;
import org.springframework.samples.petclinic.model.ProductoVendido;
import org.springframework.samples.petclinic.repository.ProductoVendidoRepository;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.ProductReviewService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class ProductReviewController {
	
	@Autowired
	private UserController userController;
	@Autowired
	private ClientService clientService;
	@Autowired
	private ProductService prodService;
	@Autowired
	private ProductoVendidoRepository prodVendService;
	@Autowired
	private OrderController oc;
 	@Autowired
	private ProductReviewService prodReviewService;

	public ProductReviewController(ProductService prodService, ProductoVendidoRepository prodVendService,
			ProductReviewService prodReviewService, ClientService clientService) {
		
		this.clientService = clientService;
		this.prodService = prodService;
		this.prodVendService = prodVendService;
		this.prodReviewService = prodReviewService;
	}

	@GetMapping()
	public String addProductReview(ModelMap modelmap) {

		if (clientService.esClient()) {

			ProductReview prodReview = new ProductReview();
			List<ProductoVendido> productVend = (List<ProductoVendido>) prodVendService.findAll();
			modelmap.addAttribute("prodReview", prodReview);
			modelmap.addAttribute("productVend", productVend);
			return "reviews/newProductReview";

		} else {
			modelmap.clear();
			return "/users/createClientForm";
		}

	}

	// nueva reseña al hotel
	@PostMapping(path = "shop/products/review/{productId}")
	public String saveReview(@Valid ProductReview review, BindingResult result, ModelMap modelmap,
			@PathVariable("productId") Integer productId) { 
		if (result.hasErrors()) {
			modelmap.addAttribute("review", review);
			modelmap.addAttribute("message",
					result.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList()));

			return addProductReview(modelmap);

		} else {

			int clientId = clientService.devolverClientId();
			Client client = clientService.findById(clientId);
			ProductoVendido prodVend = prodVendService.findById(productId).get();
			Product prod = prodService.findProductByName(prodVend.getNombre());
			review.setClient(client);
			review.setProductoVendido(prodVend);
			modelmap.addAttribute("productId", productId);
			review.setProduct(prod);
			prodReviewService.save(review);
//			prodService.addRate(review);

			log.info("Se ha creado la review correctamente");
			modelmap.addAttribute("message", "The product review has been created successfully");
			return oc.myOrderList(modelmap);
		}
	}
}
