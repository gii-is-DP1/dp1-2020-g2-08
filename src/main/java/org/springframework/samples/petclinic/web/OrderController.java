package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.repository.ClientRepository;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OrderService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
@RequestMapping("/newOrder")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ClientService clientService;
	
	
	@Autowired
	public OrderController(OrderService orderService,ProductService productService,ClientService clientService) {
 
		this.orderService = orderService;
		this.productService = productService;
		this.clientService=clientService ;

	}
	
//	@InitBinder("order")
//	public void initPetBinder(WebDataBinder dataBinder) {
//		dataBinder.setValidator(new OrderValidator());
//	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	public void devolverClient(ModelMap modelmap) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object sesion = auth.getPrincipal();
		UserDetails us = null;
		if (sesion instanceof UserDetails) {
			us = (UserDetails) sesion;
		}

		String res = us.getUsername();

		if (us.getAuthorities().iterator().next().getAuthority().equals("client")) {
Client c = ( clientService.findAllList().stream().filter(x -> x.getUser().getUsername().equals(res))).collect(Collectors.toList()).get(0);
			
			Integer id =c.getId();

			modelmap.addAttribute("clientId", id);
			modelmap.addAttribute("client", c);

		} else {
			modelmap.addAttribute("message", "No estas logueado como cliente");

		}

	}
	
	//Para comprar 1 solo articulo sin añadir al carro /newOrder/buy/(productId)
	@GetMapping(path = "/buy/{productId}")
	public String compraProducto(ModelMap modelmap, @PathVariable("productId") Integer productId) {
	
		if (clientService.esClient()) {
		Product p = productService.findProductById(productId).get();	
		Order order = new Order();	
		modelmap.put("order", order);
		modelmap.put("product", p);
		
		 return "order/newOrder";
		
		}
		else {
			modelmap.put("message","Para hacer compras en la tiendas tienes que iniciar sesion como cliente");
			return "/login";
			
		}
		
	}
	
	@PostMapping(path = "/buy/{productId}")
	public String guardaCompraProducto(ModelMap modelmap, @PathVariable("productId") Integer productId,  Order order, BindingResult result) {
		int clientId = clientService.devolverClientId();
		Client client = clientService.findById(clientId);		
		Set<Product> products = new HashSet<Product>();		
		Product p = productService.findProductById(productId).get();
		products.add(p);
		
		if (result.hasErrors()) {	
			
			modelmap.put("order", order);
			modelmap.put("product", productService.findProductById(productId));
			modelmap.put("message", "Hubo un error al crear el pedido");
			return "order/newOrder";
		}
		
		else {
			order.setProducts(products);
			order.setOrderDate(LocalDate.now());
			order.setDeliveryDate(LocalDate.now().plusDays(7));
			order.setClient(client);
			order.setPriceOrder(p.getPrice());
			order.setState("In progress");
			orderService.save(order);
			modelmap.put("message", "Su pedido se ha completado con exito");
			return "order/orderSummary";
		}
	
	}
	
	/* 		Variables del formulario
	 * state
	 * Country
	 * Address
	 * City
	 * postalCode
	 * 		Variables que se inrtoducen en el controlador de forma manual
	 * 				ClientId
	 * 				deliveryDate
	 * 				OrderDate
	 * 				priceOrder
	 * 				products
	 * 
	 * */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
