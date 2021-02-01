package org.springframework.samples.petclinic.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.repository.CouponRepository;
import org.springframework.samples.petclinic.repository.OrderRepository;
import org.springframework.samples.petclinic.repository.ProductoVendidoRepository;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.CouponService;
import org.springframework.samples.petclinic.service.OrderService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.UserService;
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
	private CouponRepository couponRepo;

	
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
	@MockBean
	private CouponService couponService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(value = "spring")
    @Test
    void testAddProductToCart() throws Exception {
		if(clientService.esClient()) {
		mockMvc.perform(get("/shop/add/{productId}",2)).
			andExpect(status().isOk()).
			andExpect(view().name("shop/carrito/carrito"));
		} else {
		mockMvc.perform(get("/shop/add/{productId}",2)).
			andExpect(status().isOk()).
			andExpect(view().name("shop/home"));
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testMostrarCarrito() throws Exception {
		if(clientService.esClient()) {
			mockMvc.perform(get("/shop/carrito")).
			andExpect(status().isOk()).
			andExpect(view().name("shop/carrito/carrito"));
		} else {
			mockMvc.perform(get("/shop/carrito")).
			andExpect(status().isOk()).
			andExpect(view().name("shop/home"));
		}
		
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcesarPedido() throws Exception {
		if(clientService.esClient()) {
			mockMvc.perform(get("/shop/carrito/complete")).
			andExpect(status().isOk()).
			andExpect(view().name("/shop/carrito/carrito"));
		} else {
			mockMvc.perform(get("/shop/carrito/complete")).
			andExpect(status().isOk()).
			andExpect(view().name("shop/home"));
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testTerminarPedido() throws Exception {
		if(clientService.esClient()) {
			mockMvc.perform(post("/shop/carrito/complete").
				with(csrf())).
				andExpect(status().isOk()).
				andExpect(view().name("/shop/carrito/carrito"));
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testCompraProducto() throws Exception {
		if(clientService.esClient()) {
			mockMvc.perform(get("/shop/buy/{productId}", 1)).
			andExpect(status().isOk()).
			andExpect(view().name("order/newOrder"));	
		} else {
			mockMvc.perform(get("/shop/buy/{productId}", 1)).
			andExpect(status().isOk()).
			andExpect(view().name("shop/home"));
		}
	}

	@WithMockUser(value = "spring", username = "mangarmar", password = "mangarmar", roles = "client")
	@Test
	void testGuardaCompraProducto() throws Exception {
		mockMvc.perform(post("/shop/buy/{productId}", 1).
			with(csrf())).
			andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testMyOrderList() throws Exception {
		if(clientService.esClient()) {
			mockMvc.perform(get("/shop/myOrders")).
			andExpect(status().isOk()).
			andExpect(view().name("shop/myOrders"));
		} else {
			mockMvc.perform(get("/shop/myOrders")).
			andExpect(status().isOk()).
			andExpect(view().name("shop/home"));
		}
		
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProductsByOrder() throws Exception {
		if(clientService.esClient() || clientService.esAdminShop()) {
			mockMvc.perform(get("/shop/view/products/{orderId}", 1)).
			andExpect(status().isOk()).
			andExpect(view().name("shop/productsByOrder"));
		} else {
			mockMvc.perform(get("/shop/view/products/{orderId}", 1)).
			andExpect(status().isOk()).
			andExpect(view().name("shop/home"));
		}
		
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testCancelarVenta() throws Exception {
		if(clientService.esClient()) {
			mockMvc.perform(get("/shop/carrito/reset")).
			andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/shop/carrito/reset")).
			andExpect(status().isOk()).
			andExpect(view().name("shop/home"));
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testQuitarDelCarrito() throws Exception {
		if(clientService.esClient()) {
			mockMvc.perform(get("/shop/carrito/remove/{indice}", 1)).
			andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/shop/carrito/remove/{indice}", 1)).
			andExpect(status().isOk()).
			andExpect(view().name("shop/home"));
		}
	}
}
