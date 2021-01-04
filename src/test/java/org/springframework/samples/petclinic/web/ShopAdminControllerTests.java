package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

//@WebMvcTest(controllers=ShopAdminController.class,
//excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
//excludeAutoConfiguration= SecurityConfiguration.class)
public class ShopAdminControllerTests {
	
//	@Autowired
//	private ShopAdminController adminController;
//	
//	@MockBean
//	private ProductService productService;
//	
//	@MockBean
//	private ClientService clientService;
//	
//	@MockBean
//	private UserService userService;
//	        
//    @MockBean
//    private AuthoritiesService authoritiesService; 
//    
//    @Autowired
//	private MockMvc mockMvc;
//    
//    private Product product1;
//    
//    
//
//    @WithMockUser(value = "spring")
//    @Test
//    void testProductList() throws Exception {
//    	mockMvc.perform(get("/products")).andExpect(status().isOk())
//		.andExpect(view().name("shop/admin/ProductList"));
//    }
//    
//    @WithMockUser(value = "spring")
//    @Test
//    void testAddPorduct() throws Exception {
//    	
//    }
//    
//    @WithMockUser(value = "spring")
//    @Test
//    void testSave() throws Exception {
//    	
//    }
//    
//    @WithMockUser(value = "spring")
//    @Test
//    void testDeleteProduct() throws Exception {
//


}
