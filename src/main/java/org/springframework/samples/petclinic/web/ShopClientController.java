package org.springframework.samples.petclinic.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class ShopClientController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private ShopService shopService;
	
	private static List<String> categoryList = Arrays.asList("Pets", "Food", "Toys", "Accessories");
	private static List<String> offerOptions = Arrays.asList("Yes", "No");
	
	public ShopClientController(ProductService productService) {

		this.productService = productService;
	}
	
	@GetMapping()
	public String productList(ModelMap modelmap) {
		String view = "shop/home";
//		Iterable<Product> products = productService.findAll();
//		int productsNumber = productService.productCount();
//
//		modelmap.addAttribute("product", products);
//		modelmap.addAttribute("productsNumber", productsNumber);
//
		return view;

	}
	
	
	
	

}
