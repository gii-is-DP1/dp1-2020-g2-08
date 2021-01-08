package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.repository.CouponRepository;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
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
	private CouponRepository couponRepository;
	
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
    
    private Product product1;
    
    @WithMockUser(username ="admin1", password = "4dm1n", value = "spring")
    @Test
    void testProductList() throws Exception {
    	mockMvc.perform(get("/shop/admin/products")).
    		andExpect(status().isOk()).
    		andExpect(view().name("shop/admin/ProductList"));
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testAddPorduct() throws Exception {
    	mockMvc.perform(get("/shop/admin/products/add")).
    		andExpect(status().isOk()).
    		andExpect(view().name("shop/admin/newProduct"));
    }
    
//    @WithMockUser(value = "spring")
//    @Test
//    void testSave() throws Exception {
//    	
//    }
    
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
    
//    @WithMockUser(value = "spring")
//    @Test
//    void testProcessUpdateForm() throws Exception {
//    	
//    }

    @WithMockUser(value = "spring")
    @Test
    void testPetList() throws Exception {
    	mockMvc.perform(get("/shop/admin/products/{category}", 1)).
    		andExpect(status().isOk()).
    		andExpect(view().name("shop/admin/productByCategoryAdmin"));
    }
    
//    @WithMockUser(value = "spring")
//    @Test
//    void testClientList() throws Exception {
//    	mockMvc.perform(get("/shop/admin/clients")).
//    		andExpect(status().isOk()).
//    		andExpect(view().name("shop/admin/ClientsList"));
//    }
    
    @WithMockUser(value = "spring")
    @Test
    void testAddCoupon() throws Exception {
    	mockMvc.perform(get("/shop/admin/coupons/add")).
    		andExpect(status().isOk()).
    		andExpect(view().name("shop/admin/newCoupon"));
    }
    
//    @WithMockUser(value = "spring")
//    @Test
//    void testSave() throws Exception {
//    	
//    }
    
    @WithMockUser(value = "spring")
    @Test
    void testCouponsList() throws Exception {
    	mockMvc.perform(get("/shop/admin/coupons")).
    		andExpect(status().isOk()).
    		andExpect(view().name("shop/admin/couponList"));
    }
}
