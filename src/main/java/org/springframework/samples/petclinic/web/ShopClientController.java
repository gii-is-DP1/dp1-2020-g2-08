package org.springframework.samples.petclinic.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
//		Iterable<Product> products = productService.findAll();
//		int productsNumber = productService.productCount();
//
//		modelmap.addAttribute("product", products);
//		modelmap.addAttribute("productsNumber", productsNumber);
//
		return view;

	}

	@GetMapping(path = "/products/pets")
	public String petList(ModelMap modelmap) {
		String view = "shop/products/pets";
		List<Product> products = (List<Product>) productService.findAll();
		List<Product> productsByCategory = products.stream().filter(x -> x.getCategory().equals("Pets"))
				.collect(Collectors.toList());
		int productsNumber = productsByCategory.size();

		modelmap.addAttribute("product", productsByCategory);
		modelmap.addAttribute("productsNumber", productsNumber);

		return view;

	}

	@GetMapping(path = "products/food")
	public String foodList(ModelMap modelmap) {
		String view = "shop/products/food";
		List<Product> products = (List<Product>) productService.findAll();
		List<Product> productsByCategory = products.stream().filter(x -> x.getCategory().equals("Food"))
				.collect(Collectors.toList());
		int productsNumber = productsByCategory.size();

		modelmap.addAttribute("product", productsByCategory);
		modelmap.addAttribute("productsNumber", productsNumber);

		return view;

	}

	@GetMapping(path = "products/toys")
	public String toysList(ModelMap modelmap) {
		String view = "shop/products/toys";
		List<Product> products = (List<Product>) productService.findAll();
		List<Product> productsByCategory = products.stream().filter(x -> x.getCategory().equals("Toys"))
				.collect(Collectors.toList());
		int productsNumber = productsByCategory.size();

		modelmap.addAttribute("product", productsByCategory);
		modelmap.addAttribute("productsNumber", productsNumber);

		return view;

	}

	@GetMapping(path = "products/accessories")
	public String accessoriesList(ModelMap modelmap) {
		String view = "shop/products/accessories";
		List<Product> products = (List<Product>) productService.findAll();
		List<Product> productsByCategory = products.stream().filter(x -> x.getCategory().equals("Accessories"))
				.collect(Collectors.toList());
		int productsNumber = productsByCategory.size();

		modelmap.addAttribute("product", productsByCategory);
		modelmap.addAttribute("productsNumber", productsNumber);

		return view;

	}

}
