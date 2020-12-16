package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.ProductService;

import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ClientSpec;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop/admin")
public class ShopAdminController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ClientService clientService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthoritiesService authoritiesService;
	
	private static List<String> categoryList = Arrays.asList("Pets", "Food", "Toys", "Accessories");
	private static List<String> offerOptions = Arrays.asList("Yes", "No");

	@Autowired
	public ShopAdminController(ProductService productService, UserService userService, AuthoritiesService authoritiesService) {
		this.productService = productService;
		this.userService = userService;
		this.authoritiesService = authoritiesService;
	}

	@GetMapping(path = "/products")
	public String productList(ModelMap modelmap) {
		String view = "shop/admin/ProductList";
		Iterable<Product> products = productService.findAll();
		int productsNumber = productService.productCount();

		modelmap.addAttribute("product", products);
		modelmap.addAttribute("productsNumber", productsNumber);

		return view;

	}

	@GetMapping(path = "/products/add")
	public String addProduct(ModelMap modelmap) {
		String view = "shop/admin/newProduct";
		modelmap.addAttribute("product", new Product());
		modelmap.addAttribute("categories", categoryList);
		modelmap.addAttribute("offers", offerOptions);
		return view;

	}

	@PostMapping(path = "/products/save")
	public String save(@PathParam("category") String category, @PathParam("name") String name,
			@PathParam("price") Double price, @PathParam("inOffer") String inOffer, ModelMap modelmap) {

		Product product = new Product();
		product.setCategory(category);
		product.setName(name);
		product.setPrice(price);
		product.setInOffer(inOffer);

		productService.save(product);
		modelmap.addAttribute("message", "New product added");
		return "redirect:/shop/admin/products";

	}

	@GetMapping(path = "/products/delete/{productId}")
	public String deleteProduct(@PathVariable("productId") int productId, ModelMap modelmap) {
		Optional<Product> product = productService.findProductById(productId);

		if (product.isPresent()) {
			productService.delete(product.get());
			modelmap.addAttribute("message", "Product delete succesfully");

		} else {
			modelmap.addAttribute("message", "Product not found");
		}
		String view = productList(modelmap);
		return view;
	}

	@GetMapping(path = "/products/edit/{productId}")
	public String edit(@PathVariable("productId") int productId, ModelMap modelmap) {
		Optional<Product> product = this.productService.findProductById(productId);
		modelmap.put("product", product);
		modelmap.addAttribute("categories", categoryList);
		modelmap.addAttribute("offers", offerOptions);
		return "shop/admin/editProduct";

	}

	@PostMapping(value = "/products/edit/{productId}")
	public String processUpdateForm(@Valid Product product, BindingResult result,
			@PathVariable("productId") int productId, ModelMap modelmap) throws DuplicatedPetNameException {
		if (result.hasErrors()) {
			modelmap.put("product", product);
			modelmap.addAttribute("categories", categoryList);
			modelmap.addAttribute("offers", offerOptions);
			return "shop/admin/editProduct";
		} else {
			Product productToUpdate = this.productService.findProductById(productId).get();
			productToUpdate.setCategory(product.getCategory());
			productToUpdate.setName(product.getName());
			productToUpdate.setPrice(product.getPrice());
			productToUpdate.setInOffer(product.getInOffer());
			this.productService.save(productToUpdate);
			return "redirect:/shop/admin/products";
		}
	}
	
	@GetMapping("/products/{category}")
	public String productListByCategory(@PathVariable ("category") String category, ModelMap modelmap) {
		String view = "shop/admin/ProductList";
		List<Product> products = (List<Product>) productService.findAll();
		List<Product> productsByCategory = products.stream().filter(x->x.getCategory().equals(category)).collect(Collectors.toList());
		modelmap.addAttribute("product", productsByCategory);


		return view;

	}
	
		@GetMapping(path = "/products/pets" )
		public String petList(ModelMap modelmap) {
			String view = "shop/admin/Pets";
			List<Product> products = (List<Product>) productService.findAll();
			List<Product> productsByCategory = products.stream().filter(x->x.getCategory().equals("Pets")).collect(Collectors.toList());
			int productsNumber = productsByCategory.size();
			
			
			modelmap.addAttribute("product", productsByCategory);
			modelmap.addAttribute("productsNumber", productsNumber);
	
	
			return view;
	
		}
	
	@GetMapping(path = "/products/food" )
	public String foodList(ModelMap modelmap) {
		String view = "shop/admin/Food";
		List<Product> products = (List<Product>) productService.findAll();
		List<Product> productsByCategory = products.stream().filter(x->x.getCategory().equals("Food")).collect(Collectors.toList());
		int productsNumber = productsByCategory.size();

		modelmap.addAttribute("product", productsByCategory);
		modelmap.addAttribute("productsNumber", productsNumber);


		return view;

	}
	
	@GetMapping(path = "/products/toys" )
	public String toysList(ModelMap modelmap) {
		String view = "shop/admin/Toys";
		List<Product> products = (List<Product>) productService.findAll();
		List<Product> productsByCategory = products.stream().filter(x->x.getCategory().equals("Toys")).collect(Collectors.toList());
		int productsNumber = productsByCategory.size();

		
		modelmap.addAttribute("product", productsByCategory);
		modelmap.addAttribute("productsNumber", productsNumber);



		return view;

	}
	
	@GetMapping(path = "/products/accessories" )
	public String accessoriesList(ModelMap modelmap) {
		String view = "shop/admin/Accessories";
		List<Product> products = (List<Product>) productService.findAll();
		List<Product> productsByCategory = products.stream().filter(x->x.getCategory().equals("Accessories")).collect(Collectors.toList());
		int productsNumber = productsByCategory.size();

		
		
		modelmap.addAttribute("product", productsByCategory);
		modelmap.addAttribute("productsNumber", productsNumber);



		return view;

	}
	
	@GetMapping(path = "/clients" )
	public String clientsList(ModelMap modelmap) {
		modelmap.clear();
		String view = "shop/admin/ClientsList";
		Iterable<Client> clients = clientService.findAll();	
		int clientsNumber = clientService.clientCount();
		modelmap.addAttribute("clientsNumber", clientsNumber);
		modelmap.addAttribute("clients", clients );

		return view;

	}


}
