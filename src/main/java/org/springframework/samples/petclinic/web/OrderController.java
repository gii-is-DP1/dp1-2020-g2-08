package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.ProductoParaVenta;
import org.springframework.samples.petclinic.model.ProductoVendido;
import org.springframework.samples.petclinic.repository.OrderRepository;
import org.springframework.samples.petclinic.repository.ProductoVendidoRepository;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.OrderService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class OrderController {

	@Autowired
	private ProductoVendidoRepository productoVendidoRepository;
	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private ProductoVendidoRepository productoVendidoRepo;

	@Autowired
	private ProductService productService;

	@Autowired
	private ClientService clientService;

	@Autowired
	public OrderController(OrderService orderService, ProductService productService, ClientService clientService) {

		this.orderService = orderService;
		this.productService = productService;
		this.clientService = clientService;

	}

//	@InitBinder("order")
//	public void initPetBinder(WebDataBinder dataBinder) {
//		dataBinder.setValidator(new OrderValidator());
//	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	private List<ProductoParaVenta> obtenerCarrito(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<ProductoParaVenta> carrito = (List<ProductoParaVenta>) request.getSession().getAttribute("carrito");
		if (carrito == null) {
			carrito = new ArrayList<>();
		}
		return carrito;
	}

	private void guardarCarrito(List<ProductoParaVenta> carrito, HttpServletRequest request) {
		request.getSession().setAttribute("carrito", carrito);
	}

	private void limpiarCarrito(HttpServletRequest request) {
		this.guardarCarrito(new ArrayList<>(), request);
	}

	@GetMapping(value = "shop/addCarrito")
	public String generaCarrito(ModelMap model, HttpServletRequest request) {
		añadeCarritoAutomatico(request);
		double total = 0.0;
		List<ProductoParaVenta> carrito = this.obtenerCarrito(request);
		for (ProductoParaVenta p : carrito)
			total += p.getTotal();
		model.addAttribute("total", total);
		model.addAttribute("carrito", carrito);
		return "shop/carrito/carrito";
	}

	@GetMapping(value = "shop/add/{productId}")
	public String addProductToCart(ModelMap model, HttpServletRequest request,
			@PathVariable("productId") int productId) {
		añadeCarrito(request, productId);
		double total = 0.0;
		List<ProductoParaVenta> carrito = this.obtenerCarrito(request);
		for (ProductoParaVenta p : carrito)
			total += p.getTotal();
		model.addAttribute("total", total);
		model.addAttribute("carrito", carrito);
		return "shop/carrito/carrito";
	}

	@GetMapping(value = "shop/carrito")
	public String mostrarCarrito(ModelMap model, HttpServletRequest request) {

		double total = 0.0;
		List<ProductoParaVenta> carrito = this.obtenerCarrito(request);
		for (ProductoParaVenta p : carrito)
			total += p.getTotal();
		model.addAttribute("total", total);

		return "shop/carrito/carrito";
	}

	
	private void añadeCarrito(HttpServletRequest request, int productId) {
		Product productoActual = productService.findProductById(productId).get();
		List<ProductoParaVenta> res = obtenerCarrito(request);
		ProductoParaVenta p = new ProductoParaVenta();

		p.setCantidad(1);
		p.setCategory(productoActual.getCategory());
		p.setInOffer(productoActual.getInOffer());
		p.setName(productoActual.getName());
		p.setPrice(productoActual.getPrice());

		res.add(p);

		guardarCarrito(res, request);

	}
	
	
	
	@GetMapping(value = "shop/carrito/complete")
	public String procesarPedido(HttpServletRequest request, ModelMap modelmap) {
		List<ProductoParaVenta> carrito = this.obtenerCarrito(request);
		// Si no hay carrito o está vacío, regresamos inmediatamente
		if (carrito == null || carrito.size() <= 0) {
			modelmap.put("message", "El carrito esta vacio");
			return "/shop/carrito/carrito";
		}
		Order order = new Order();
		modelmap.put("order",order);
		return "order/newOrderCarrito";
	}
	
	
	@PostMapping(value = "shop/carrito/complete")
	public String terminarPedido(HttpServletRequest request, ModelMap modelmap,  Order order,BindingResult result) {
		
		
		if (result.hasErrors()) {
			modelmap.put("message", "No se ha podido procesar el pedido");
			modelmap.put("order",order);
			return "order/newOrderCarrito";
		}
		else {
			int clientId = clientService.devolverClientId();
			Client client = clientService.findById(clientId);
			List<ProductoParaVenta> carrito = this.obtenerCarrito(request);
			// Si no hay carrito o está vacío, regresamos inmediatamente
			if (carrito == null || carrito.size() <= 0) {
				modelmap.put("message", "El carrito esta vacio");
				return "/shop/carrito/carrito";
			}
			Order o = new Order();
			o.setClient(client);
			o.setDeliveryDate(LocalDate.now().plusDays(7));
			o.setOrderDate(LocalDate.now());
			o.setState("In Progress");
			
			o.setAddress(order.getAddress());
			o.setCity(order.getCity());
			o.setCountry(order.getCountry());
			o.setPostalCode(order.getPostalCode());
			
			orderRepo.save(o) ;

			// Recorrer el carrito
			for (ProductoParaVenta productoParaVender : carrito) {

				// Creamos un nuevo producto que será el que se guarda junto con la venta
				ProductoVendido productoVendido = new ProductoVendido();
				productoVendido.setCantidad(productoParaVender.getCantidad());
				productoVendido.setNombre(productoParaVender.getName());
				productoVendido.setPrecio(productoParaVender.getPrice());
				productoVendido.setOrder(o);

				// Y lo guardamos
				productoVendidoRepo.save(productoVendido);
			}

			// Al final limpiamos el carrito
			this.limpiarCarrito(request);
			// e indicamos una venta exitosa
			modelmap.put("message", "Pedido realizado con exito");
			return "shop/home";
		}
		}
			
		
		

	
	
	
	
	
	
	
	
	private void añadeCarritoAutomatico(HttpServletRequest request) {
		List<Product> products = (List<Product>) productService.findAll();
		List<ProductoParaVenta> res = obtenerCarrito(request);

		for (int i = 0; i < products.size(); i++) {
			Product productoActual = products.get(i);
			ProductoParaVenta p = new ProductoParaVenta();
			p.setCantidad(1);
			p.setCategory(productoActual.getCategory());
			p.setInOffer(productoActual.getInOffer());
			p.setName(productoActual.getName());
			p.setPrice(productoActual.getPrice());

			res.add(p);

		}
		guardarCarrito(res, request);

	}

	// Para comprar 1 solo articulo sin añadir al carro /shop/buy/(productId)
	@GetMapping(path = "/shop/buy/{productId}")
	public String compraProducto(ModelMap modelmap, @PathVariable("productId") Integer productId) {

		if (clientService.esClient()) {
			Product p = productService.findProductById(productId).get();
			Order order = new Order();
			modelmap.put("order", order);
			modelmap.put("product", p);

			return "order/newOrder";

		} else {
			modelmap.put("message", "Para hacer compras en la tiendas tienes que iniciar sesion como cliente");
			return "users/createOwnerForm";

		}

	}

	@PostMapping(path = "shop/buy/{productId}")
	public String guardaCompraProducto(ModelMap modelmap, @PathVariable("productId") Integer productId, Order order,
			BindingResult result) {
		int clientId = clientService.devolverClientId();
		Client client = clientService.findById(clientId);
		ProductoVendido p = new ProductoVendido();
		Product producto = productService.findProductById(productId).get();

		if (result.hasErrors()) {

			modelmap.put("order", order);
			modelmap.put("product", productService.findProductById(productId));
			modelmap.put("message", "Hubo un error al crear el pedido");
			return "order/newOrder";
		}

		else {

			order.setOrderDate(LocalDate.now());
			order.setDeliveryDate(LocalDate.now().plusDays(7));
			order.setClient(client);

			order.setState("In progress");
			orderService.save(order);

			p.setCantidad(1);
			p.setNombre(producto.getName());
			p.setPrecio(producto.getPrice());
			p.setOrder(order);

			productoVendidoRepo.save(p);
			modelmap.put("message", "Su pedido se ha completado con exito");
			return "shop/home";
		}

	}

}
