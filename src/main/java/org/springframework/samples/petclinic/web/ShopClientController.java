package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/shop")
public class ShopClientController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ClientService clientService;

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

	@GetMapping(path = "/products/{category}")
	public String petList(@PathVariable (value="category") String category, ModelMap modelmap) {
		String view = "shop/products/productByCategory";
		List<Product> products = (List<Product>) productService.findAll();
		List<Product> productsByCategory = products.stream().filter(x -> x.getCategory().equals(category))
				.collect(Collectors.toList());
		int productsNumber = productsByCategory.size();

		modelmap.addAttribute("product", productsByCategory);
		modelmap.addAttribute("productsNumber", productsNumber);
		modelmap.addAttribute("category", category);
		log.info("Se muestran los productos de la categoria "+category);
		return view;

	}

	
	@GetMapping(path = "/products/review/{productId}")
	public String productReview(ModelMap modelmap, @PathVariable (value="productId") Integer productId) {
		
		if (clientService.esClient()) {
			Review review = new Review();
			List<Product> products = (List<Product>) productService.findAll();
			modelmap.addAttribute("review", review);
			modelmap.addAttribute("product", products);
			log.info("Se muestra el formulario de valorar producto");
			return "reviews/newProductReview";
			
		}
		
		else {
			modelmap.addAttribute("message", "Para hacer eso tienes que estar logueado como cliente de la tienda");
			return productList(modelmap);
		}
			

		

	}


}
