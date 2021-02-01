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
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ShopClientController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class ShopClientControllerTests {

	@Autowired
	private ShopClientController shopClientController;

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
	
	
	@WithMockUser(value = "spring")
    @Test
    void testProductList() throws Exception {
		mockMvc.perform(get("/shop")).
			andExpect(status().isOk()).
			andExpect(view().name("shop/home"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testPetList() throws Exception {
		mockMvc.perform(get("/shop/products/{category}", 1)).
			andExpect(status().isOk()).
			andExpect(view().name("shop/products/productByCategory"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
    @Test
    void testProductReview() throws Exception {
		if(clientService.esClient()) {
			mockMvc.perform(get("/shop/products/review/{productId}", 1)).
			andExpect(status().isOk()).
			andExpect(view().name("reviews/newProductReview"));
		} else {
		mockMvc.perform(get("/shop/products/review/{productId}", 1)).
			andExpect(status().isOk()).
			andExpect(view().name("shop/home"));
		}
	}
}
