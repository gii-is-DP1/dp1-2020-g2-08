package org.springframework.samples.petclinic.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Coupon;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.repository.CouponRepository;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.CouponService;
import org.springframework.samples.petclinic.service.OrderService;
import org.springframework.samples.petclinic.service.ProductService;

import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
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
@RequestMapping("/shop/admin")
public class ShopAdminController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CouponService couponService;
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private ClientService clientService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthoritiesService authoritiesService;
	
	private static List<String> categoryList = Arrays.asList("Pets", "Food", "Toys", "Accessories");
	private static List<String> offerOptions = Arrays.asList("Yes", "No");
	
	@InitBinder("product")
	public void initPetBinder(WebDataBinder dataBinder) {

		dataBinder.setValidator(new ProductValidator());

	}

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
	public String save(@Valid Product product, BindingResult result, @PathParam("category") String category, @PathParam("name") String name,
			@PathParam("price") Double price, @PathParam("inOffer") String inOffer, ModelMap modelmap) {

		if (result.hasErrors()) {
			modelmap.addAttribute("product", product);
			modelmap.addAttribute("message", result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList()));
			
			return addProduct(modelmap);
			

		}else {

	
		product.setCategory(category);
		product.setName(name);
		product.setPrice(price);
		product.setInOffer(inOffer);

		productService.save(product);
		modelmap.addAttribute("message", "New product added");	
		return "redirect:/shop/admin/products";
		}

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
	
	@GetMapping(path = "/products/{category}") // {category}
	public String petList(@PathVariable (value="category") String category, ModelMap modelmap) {
		String view = "shop/admin/productByCategoryAdmin";
		List<Product> products = (List<Product>) productService.findAll();
		List<Product> productsByCategory = products.stream().filter(x -> x.getCategory().equals(category))
				.collect(Collectors.toList());
		int productsNumber = productsByCategory.size();

		modelmap.addAttribute("product", productsByCategory);
		modelmap.addAttribute("productsNumber", productsNumber);
		modelmap.addAttribute("category", category);

		return view;

	}
	
	
	
	@GetMapping(path = "/clients" )
	public String clientsList(ModelMap modelmap) {
		modelmap.clear();
		String view = "shop/admin/ClientsList";
		List<Client> clients = (List<Client>) clientService.findAll();	
		int clientsNumber = clientService.clientCount();
		modelmap.addAttribute("clientsNumber", clientsNumber);
		modelmap.addAttribute("clients", clients );
//		modelmap.addAttribute("clientCoupons", clients.get(0).getCoupons().stream().findFirst().get() );
		return view;

	}
	
	@GetMapping(path = "/coupons/add")
	public String addCoupon(ModelMap modelmap) {
		String view = "shop/admin/newCoupon";
		modelmap.addAttribute("coupon", new Coupon());
		return view;
	}

	@PostMapping(path = "/coupons/save")
	public String save(@Valid Coupon coupon, BindingResult result,  ModelMap modelmap) {
		
		if (result.hasErrors()) {
			modelmap.addAttribute("coupon", coupon);
			modelmap.addAttribute("message", result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList()));
			
			return addCoupon(modelmap);
			

		}else {

	
			couponRepository.save(coupon);
		
		modelmap.addAttribute("message", "New coupon added");	
		return couponsList(modelmap);
		}

		
	



}
	@GetMapping(path = "/coupons/delete/{couponId}" )
	public String removeCoupon(ModelMap modelmap,@PathVariable ("couponId") int couponId) {
		
		Coupon coupon = couponRepository.findById(couponId).get();
		
		couponService.delete(coupon);
		modelmap.addAttribute("message", "El cupon se ha eliminado correctamente");
			
		return couponsList(modelmap);

	}
	
	@GetMapping(path = "/coupons" )
	public String couponsList(ModelMap modelmap) {
		modelmap.clear();
		String view = "shop/admin/couponList";
		List<Coupon> coupons = (List<Coupon>) couponRepository.findAll();	
			modelmap.addAttribute("coupons", coupons );
				return view;

	}
	
	@GetMapping(path = "/coupons/{clientId}" )
	public String clientCouponsList(ModelMap modelmap,@PathVariable("clientId") int clientId) {
		Client client = clientService.findById(clientId);
		Set<Coupon> coupons =  		couponService.findCouponByClientId(clientId);	
		Iterable<Coupon> coupons2 =  		 couponRepository.findAll();
			modelmap.addAttribute("couponsClient", coupons );
			modelmap.addAttribute("coupons", coupons2 );
			modelmap.addAttribute("client", client );
			
				return  "shop/admin/couponListClient";

	}
	
	@GetMapping(path = "/orders" )
	public String ordersList(ModelMap modelmap) {
		
		String view = "shop/admin/orderList";
		
		List<Order> orders = (List<Order>) orderService.findAll();
		
		modelmap.addAttribute("ordersNumber", orders.size());
		modelmap.addAttribute("orders", orders );
		
		return view;

	}
	
	@GetMapping(path = "/orders/deny/{orderId}" )
	public String denyOrder(ModelMap modelmap,@PathVariable ("orderId") int orderId) {
		
		
		
		Order order =  orderService.findOrderById(orderId).get();
		order.setState("Cancelled");
		orderService.save(order);
		modelmap.addAttribute("message", "El pedido se ha cancelado con éxito");
			
		return ordersList(modelmap);

	}
	@GetMapping(path = "/orders/confirm/{orderId}" )
	public String confirmOrder(ModelMap modelmap,@PathVariable ("orderId") int orderId) {
		Order order =  orderService.findOrderById(orderId).get();
		order.setState("Confirmed");
		orderService.save(order);
		modelmap.addAttribute("message", "El pedido se ha confirmado con éxito");
			
		return ordersList(modelmap);

	}
	
	@GetMapping(path = "/clients/{clientId}/addCoupon/{couponId}" )
	public String addCouponToClient(ModelMap modelmap,@PathVariable ("clientId") int clientId,@PathVariable ("couponId") int couponId) {
		
		
		
		Coupon coupon = couponRepository.findById(couponId).get();
		
		Client client = clientService.findById(clientId);
		
		if (client.getCoupons().contains(coupon)) {
			modelmap.addAttribute("message", "No se pudo añadir porque el cliente ya tenia ese cupon");
		}
		else {
			
		
		client.getCoupons().add(coupon);
		clientService.saveClient(client);
		modelmap.addAttribute("message", "El cupon se ha añadido al cliente");
		}	
		return clientCouponsList(modelmap, clientId); 

	}
	@GetMapping(path = "/clients/{clientId}/removeCoupon/{couponId}" )
	public String removeCouponToClient(ModelMap modelmap,@PathVariable ("clientId") int clientId,@PathVariable ("couponId") int couponId) {
		
		Coupon coupon = couponRepository.findById(couponId).get();
		
		Client client = clientService.findById(clientId);
		client.getCoupons().remove(coupon);
		clientService.saveClient(client);
		modelmap.addAttribute("message", "El cupon se ha eliminado del cliente");
			
		return clientCouponsList(modelmap, clientId);

	}

}
