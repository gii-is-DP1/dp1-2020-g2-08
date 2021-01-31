package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Coupon;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.ProductoParaVenta;
import org.springframework.samples.petclinic.model.ProductoVendido;
import org.springframework.samples.petclinic.repository.CouponRepository;
import org.springframework.samples.petclinic.repository.OrderRepository;
import org.springframework.samples.petclinic.repository.ProductoVendidoRepository;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.CouponService;
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
	private CouponRepository couponRepository;
	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private ProductoVendidoRepository productoVendidoRepo;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CouponService couponService;

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


	@GetMapping(value = "shop/add/{productId}")
	public String addProductToCart(ModelMap model, HttpServletRequest request,
			@PathVariable("productId") int productId) {
		
		if (clientService.esClient()) {
		añadeCarrito(request, productId);
		double total = 0.0;
		List<ProductoParaVenta> carrito = this.obtenerCarrito(request);
		for (ProductoParaVenta p : carrito)
			total += p.getTotal();
		model.addAttribute("total", total);
		model.addAttribute("carrito", carrito);
		return "shop/carrito/carrito";
			
		}
		
		
		
		else {
			model.addAttribute("message", "Para hacer eso tienes que estar logueado como cliente de la tienda");
			return "shop/home";
		}
			
	}

	@GetMapping(value = "shop/carrito")
	public String mostrarCarrito(ModelMap model, HttpServletRequest request) {
		
		if (clientService.esClient()) {

		double total = 0.0;
		List<ProductoParaVenta> carrito = this.obtenerCarrito(request);
		for (ProductoParaVenta p : carrito)
			total += p.getTotal();
		model.addAttribute("total", total);

		return "shop/carrito/carrito";}
		else {
			model.addAttribute("message", "Para hacer eso tienes que estar logueado como cliente de la tienda");
			return "shop/home";
		}
	}

	
	private void añadeCarrito(HttpServletRequest request, int productId) {
		
		if (clientService.esClient()) {
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
		else {
			
			
		}

	}
	
	
	
	@GetMapping(value = "shop/carrito/complete")
	public String procesarPedido(HttpServletRequest request, ModelMap modelmap) {
		
		
		if (clientService.esClient()) {
		List<ProductoParaVenta> carrito = this.obtenerCarrito(request);
		// Si no hay carrito o está vacío, regresamos inmediatamente
		if (carrito == null || carrito.size() <= 0) {
			modelmap.put("message", "El carrito esta vacio");
			return "/shop/carrito/carrito";
		}
		List<Coupon> coupons = couponService.findCouponByClientIdList(clientService.devolverClientId());
		Order order = new Order();
		modelmap.put("order",order);
		modelmap.put("coupons",coupons);
		return "order/newOrderCarrito";
	}
		else {
			modelmap.addAttribute("message", "Para hacer eso tienes que estar logueado como cliente de la tienda");
			return "shop/home";
		}
	}
	
	@PostMapping(value = "shop/carrito/complete")
	public String terminarPedido(HttpServletRequest request, ModelMap modelmap,  Order order,BindingResult result) {
		if (clientService.esClient()) {	
		
		if (result.hasErrors()) {
			modelmap.put("message", "No se ha podido procesar el pedido");
			modelmap.put("order",order);
			return "order/newOrderCarrito";
		}
		else {
			double precio=0.0;
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
			if (order.getCoupon()!=null) {
			o.setCoupon(order.getCoupon());
			client.getCoupons().remove(order.getCoupon()); // una vez usado el cupon, se elimina del cleinte (RN18)
			}
			orderRepo.save(o) ;

			// Recorrer el carrito
			for (ProductoParaVenta productoParaVender : carrito) {

				// Creamos un nuevo producto que será el que se guarda junto con la venta
				ProductoVendido productoVendido = new ProductoVendido();
				productoVendido.setCantidad(productoParaVender.getCantidad());
				productoVendido.setNombre(productoParaVender.getName());
				productoVendido.setPrecio(productoParaVender.getPrice());
				productoVendido.setOrder(o);
				precio+=productoVendido.getPrecio();
				// Y lo guardamos
				productoVendidoRepo.save(productoVendido);
			}
			//Se aplica el cupon de descuento
			if (order.getCoupon()!=null) {
				precio=(precio*((100)-(order.getCoupon().getDiscount())))/100;
			}
			
			Order orderPrice =orderService.findOrderById(o.getId()).get();
			orderPrice.setPriceOrder(precio);
			orderRepo.save(orderPrice);
			// Al final limpiamos el carrito
			this.limpiarCarrito(request);
			// e indicamos una venta exitosa
			modelmap.put("message", "Pedido realizado con exito");
			return "shop/home";
		}
		
	}
		
		
		else {
			modelmap.addAttribute("message", "Para hacer eso tienes que estar logueado como cliente de la tienda");
			return "shop/home";
		}
		}
			
		


	// Para comprar 1 solo articulo sin añadir al carro /shop/buy/(productId)
	@GetMapping(path = "/shop/buy/{productId}")
	public String compraProducto(ModelMap modelmap, @PathVariable("productId") Integer productId) {
		
		if (clientService.esClient()) {
			Product p = productService.findProductById(productId).get();
			Order order = new Order();
			modelmap.put("order", order);
			modelmap.put("product", p);
			List<Coupon> coupons = couponService.findCouponByClientIdList(clientService.devolverClientId());
			modelmap.put("coupons",coupons);
			return "order/newOrder";

		}else {
			modelmap.addAttribute("message", "Para hacer eso tienes que estar logueado como cliente de la tienda");
			return "shop/home";
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
			
			if (order.getCoupon()!=null) {// precio=(precio*((100)-(order.getCoupon().getDiscount())))/100;
				order.setPriceOrder( (producto.getPrice()*((100)-order.getCoupon().getDiscount()))/100 );
				client.getCoupons().remove(order.getCoupon()); // una vez usado el cupon, se elimina del cleinte (RN18)
			}
			else {
				order.setPriceOrder(producto.getPrice());
			}
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
	@GetMapping(path = "/shop/myOrders")
	private String myOrderList(ModelMap modelmap) {
		
		if (clientService.esClient()) {
		String view = "shop/myOrders";
		Integer clientId = clientService.devolverClientId();
		List<Order> ordersByClientId = orderService.findOrderByClientId(clientId);
		Integer ordersNumber = ordersByClientId.size();
		modelmap.addAttribute("orders", ordersByClientId);
		modelmap.addAttribute("ordersNumber",ordersNumber);

		
		return view;
	}
		
		else {
			modelmap.addAttribute("message", "Para hacer eso tienes que estar logueado como cliente de la tienda");
			return "shop/home";
		}
	}
	
	@GetMapping(path = "/shop/view/products/{orderId}")
	private String productsByOrder	(@PathVariable (value="orderId") Integer orderId, ModelMap modelmap) {
		
		if (clientService.esClient() || clientService.esAdminShop()) {
		String view = "shop/productsByOrder";
		List<ProductoVendido> productsByOrder = orderService.findProductsByOrder(orderId);
		Integer productsNumber = productsByOrder.size();
		modelmap.addAttribute("products", productsByOrder);
		modelmap.addAttribute("productsNumber",productsNumber);
		return view;}
		else {
			modelmap.addAttribute("message", "No tienes permiso para hacer eso");
			return "shop/home";
		}
	}
	
	@GetMapping(value = "/shop/carrito/reset")
	
	public String cancelarVenta(HttpServletRequest request, ModelMap modelmap) {
		if (clientService.esClient()) {
	    this.limpiarCarrito(request);
	   modelmap.addAttribute("message", "Se han eliminado todos los productos del carrito");
	            
	    return mostrarCarrito(modelmap, request);
	    }
	else {
		modelmap.addAttribute("message", "Para hacer eso tienes que estar logueado como cliente de la tienda");
		return "shop/home";
	}
}
@GetMapping(value = "/shop/carrito/remove/{indice}")
public String quitarDelCarrito(@PathVariable int indice, HttpServletRequest request, ModelMap modelmap) {
	if (clientService.esClient()) {
    List<ProductoParaVenta> carrito = this.obtenerCarrito(request);
    if (carrito != null && carrito.size() > 0 && carrito.get(indice) != null) {
        carrito.remove(indice);
        this.guardarCarrito(carrito, request);
        modelmap.addAttribute("message", "Se ha eliminado el producto del carrito");
    }
    return mostrarCarrito(modelmap, request);
    
}
    else {
		modelmap.addAttribute("message", "Para hacer eso tienes que estar logueado como cliente de la tienda");
		return "shop/home";
	}
    
}
}
