package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.repository.CouponRepository;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.CouponService;
import org.springframework.samples.petclinic.service.OrderService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ShopAdminController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class ShopAdminControllerTests {
	
	@Autowired
	private ShopAdminController adminController;
	
	@MockBean
	private OrderService orderService;
	
	@MockBean
	private CouponRepository couponRepository;
	
	@MockBean
	private CouponService couponService;
	
	@MockBean
	private ProductService productService;
	
	@MockBean
	private ClientService clientService;
	
	@MockBean
	private UserService userService;
	        
    @MockBean
    private AuthoritiesService authoritiesService; 
    
    @Autowired
	private MockMvc mockMvc;
    
    
    @WithMockUser(username ="admin1", password = "4dm1n", value = "spring")
    @Test
    void testProductList() throws Exception {
    	mockMvc.perform(get("/shop/admin/products")).
    		andExpect(status().isOk()).
    		andExpect(view().name("shop/admin/ProductList"));
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testAddProduct() throws Exception {
    	mockMvc.perform(get("/shop/admin/products/add")).
    		andExpect(status().isOk()).
    		andExpect(view().name("shop/admin/newProduct"));
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testSave() throws Exception {
    	mockMvc.perform(post("/shop/admin/products/save").
    			param("category", "Pet").
    			param("name", "Cat").
    			param("price", "12.0").
    			param("inOffer", "Yes").
    			with(csrf())).
		andExpect(status().is3xxRedirection()).
		andExpect(view().name("redirect:/shop/admin/products"));
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testDeleteProduct() throws Exception {
    	mockMvc.perform(get("/shop/admin/products/delete/{productId}", 1)).
		andExpect(status().isOk());
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testEdit() throws Exception {
    	mockMvc.perform(get("/shop/admin/products/edit/{productId}", 1)).
			andExpect(status().isOk()).
			andExpect(view().name("shop/admin/editProduct"));
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testProcessUpdateForm() throws Exception {
    	if(clientService.esAdminShop()) {
    	mockMvc.perform(post("/shop/admin/products/edit/{productId}", 1).
    			with(csrf())).
		andExpect(status().isOk()).
		andExpect(view().name("redirect:/shop/admin/products"));
    	}
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testProcessUpdateFormHasErrors() throws Exception {
    	mockMvc.perform(post("/shop/admin/products/edit/{productId}", 1)
				.with(csrf()))
	.andExpect(status().isOk())
	.andExpect(model().attributeHasErrors("product"))
	.andExpect(model().attributeHasFieldErrors("product", "name"))
	.andExpect(view().name("shop/admin/editProduct"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testPetList() throws Exception {
    	mockMvc.perform(get("/shop/admin/products/{category}", 1)).
    		andExpect(status().isOk()).
    		andExpect(view().name("shop/admin/productByCategoryAdmin"));
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testClientList() throws Exception {
    	mockMvc.perform(get("/shop/admin/clients")).
    		andExpect(status().isOk()).
    		andExpect(view().name("shop/admin/ClientsList"));
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testAddCoupon() throws Exception {
    	mockMvc.perform(get("/shop/admin/coupons/add")).
    		andExpect(status().isOk()).
    		andExpect(view().name("shop/admin/newCoupon"));
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testSaveCoupon() throws Exception {
    	mockMvc.perform(post("/shop/admin/coupons/save").
    			with(csrf())).
		andExpect(status().isOk());
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testRemoveCoupon() throws Exception {
    	mockMvc.perform(get("/shop/admin/coupons/delete/{couponId}", 1)).
    		andExpect(status().isOk());
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testCouponsList() throws Exception {
    	mockMvc.perform(get("/shop/admin/coupons")).
    		andExpect(status().isOk()).
    		andExpect(view().name("shop/admin/couponList"));
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testClientCouponsList() throws Exception {
    	mockMvc.perform(get("/shop/admin/coupons/{clientId}",1)).
    		andExpect(status().isOk()).
    		andExpect(view().name("shop/admin/couponListClient"));
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testOrderList() throws Exception {
    	mockMvc.perform(get("/shop/admin/orders")).
    		andExpect(status().isOk()).
    		andExpect(view().name("shop/admin/orderList"));
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testDenyOrder() throws Exception {
    	mockMvc.perform(get("/shop/admin/orders/deny/{orderId}",1)).
    		andExpect(status().isOk());
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testConfirmOrder() throws Exception {
    	mockMvc.perform(get("/shop/admin/orders/confirm/{orderId}",1)).
    		andExpect(status().isOk());
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testInProgressOrder() throws Exception {
    	mockMvc.perform(get("/shop/admin/orders/inProgress/{orderId}",1)).
    		andExpect(status().isOk());
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testAddCouponToClient() throws Exception {
    	mockMvc.perform(get("/shop/admin/clients/{clientId}/addCoupon/{couponId}",1,1)).
    		andExpect(status().isOk());
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testRemoveCouponToClient() throws Exception {
    	mockMvc.perform(get("/shop/admin/clients/{clientId}/removeCoupon/{couponId}",1,1)).
    		andExpect(status().isOk());
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testSalesByDate() throws Exception {
    	mockMvc.perform(get("/shop/admin/sales/{date}","today")).
    		andExpect(status().isOk()).
    		andExpect(view().name("shop/admin/sales"));
    }
    
}
