package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.ProductoParaVenta;
import org.springframework.samples.petclinic.model.ProductoVendido;
import org.springframework.samples.petclinic.repository.OrderRepository;
import org.springframework.samples.petclinic.repository.ProductoVendidoRepository;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.OrderService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=OrderController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class OrderControllerTests {

	@Autowired
	private OrderController orderController;
	
	@MockBean
	private ProductoVendidoRepository productoVendidoRepository;
	@MockBean
	private OrderRepository orderRepo;

	
	@MockBean
	private AuthoritiesService authoritiesService;
	@MockBean
	private UserService userService;
	@MockBean
	private ProductService productService;
	@MockBean
	private ClientService clientService;
	@MockBean
	private OrderService orderService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private ProductoVendido product;
	
	private Order order;
	
	@WithMockUser(value = "spring")
    @Test
    void testGeneraCarrito() throws Exception {
		mockMvc.perform(get("/shop/addCarrito")).
			andExpect(status().isOk()).
			andExpect(view().name("shop/carrito/carrito"));
	}
	
	@WithMockUser(username = "mangarmar", password = "mangarmar", value = "spring")
    @Test
    void testAddProductToCart() throws Exception {
		mockMvc.perform(get("/shop/add/{productId}",2)).
			andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testMostrarCarrito() throws Exception {
		mockMvc.perform(get("/shop/carrito")).
			andExpect(status().isOk()).
			andExpect(view().name("shop/carrito/carrito"));
	}
	
//	@BeforeEach
//	void setup() {
//
//		product = new ProductoParaVenta();
//		product.setId(5);
//		product.setName("Product 1");
//		product.setPrice(10.0);
//		product.setInOffer("Yes");
//		product.setCategory("Toys");
//		product.setCantidad(5);
//		
//		order = new Order();
//	}
	
//	@WithMockUser(value = "spring")
//    @Test
//    void testProcesarPedidoLleno() throws Exception {
//		mockMvc.perform(get("/shop/carrito/complete").
//				param("id", "5").
//				param("name", "Product 1").
//				param("price", "10.0").
//				param("inOffer", "Yes").
//				param("category", "Toys").
//				param("cantidad", "5")).
//			andExpect(status().isOk()).
//			andExpect(view().name("order/newOrderCarrito"));
//	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcesarPedidoVacio() throws Exception {
		mockMvc.perform(get("/shop/carrito/complete")).
			andExpect(status().isOk()).
			andExpect(view().name("/shop/carrito/carrito"));
	}
	
//	@WithMockUser(value = "spring")
//    @Test
//    void testTerminarPedidoVacio() throws Exception {
//		mockMvc.perform(post("/shop/carrito/complete").param("id", "5").
//				param("name", "Product 1").
//				param("price", "10.0").
//				param("inOffer", "Yes").
//				param("category", "Toys").
//				param("cantidad", "5")).
//			andExpect(view().name("/shop/carrito/carrito"));
//	}
	
//	@WithMockUser(value = "spring")
//    @Test
//    void testTerminarPedidoLleno() throws Exception {
//		
//	}
	
	
	@WithMockUser(value = "spring")
    @Test
    void testCompraProductoSinAutenticacion() throws Exception {
		mockMvc.perform(get("/shop/buy/{productId}", 1)).
			andExpect(status().isOk()).
			andExpect(view().name("users/createOwnerForm"));
	}
	
//	@WithMockUser(value = "spring")
//    @Test
//    void testCompraProductoConAutenticacion() throws Exception {
//		mockMvc.perform(get("/shop/buy/{productId}", 1)).
//		andExpect(status().isOk()).
//		andExpect(view().name("/order/newOrder"));
//	}
	
	@WithMockUser(value = "spring")
    @Test
    void testMyOrderList() throws Exception {
		mockMvc.perform(get("/shop/myOrders")).
			andExpect(status().isOk()).
			andExpect(view().name("shop/myOrders"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProductsByOrder() throws Exception {
		mockMvc.perform(get("/shop/view/products/{orderId}", 1)).
			andExpect(status().isOk()).
			andExpect(view().name("shop/productsByOrder"));
	}
	
//	@WithMockUser(value = "spring")
//    @Test
//    void testGuardaCompraProducto() throws Exception {
//		mockMvc.perform(post("/shop/buy/{productId}", 1).
//				param("id", "5").
//				param("name", "Product 1").
//				param("price", "10.0").
//				param("inOffer", "Yes").
//				param("category", "Toys").
//				param("cantidad", "5")).
//			andExpect(view().name("/shop/carrito/carrito"));
//	}
}
