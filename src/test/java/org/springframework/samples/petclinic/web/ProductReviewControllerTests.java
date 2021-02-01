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
import org.springframework.samples.petclinic.repository.ProductoVendidoRepository;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.ProductReviewService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ProductReviewController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class ProductReviewControllerTests {

	@Autowired
	private ProductReviewController productReviewController;
	
	@MockBean
	private UserController userController;
	
	@MockBean
	private ClientService clientService;
	@MockBean
	private ProductService prodService;
	@MockBean
	private ProductReviewService prodReviewService;
	
	@MockBean
	private ProductoVendidoRepository prodVendService;
	
    @MockBean
	private UserService userService;
    @MockBean
    private AuthoritiesService authoritiesService; 

	@Autowired
	private MockMvc mockMvc;
	
	
	@WithMockUser(value = "spring")
    @Test
    void testAddProductReview() throws Exception {
		if(clientService.esClient()) {
			mockMvc.perform(get("")).
			andExpect(status().isOk()).
			andExpect(view().name("reviews/newProductReview"));
		} else {
			mockMvc.perform(get("")).
			andExpect(status().isOk()).
			andExpect(view().name("/users/createClientForm"));
		}
	
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testSaveReview() throws Exception {
		if(clientService.esClient()) {
			mockMvc.perform(post("/shop/products/review/{productId}", 1).
					with(csrf())).
			andExpect(status().isOk()).
			andExpect(view().name("shop/home"));
		} else {
			mockMvc.perform(post("/shop/products/review/{productId}", 1).
					with(csrf())).
			andExpect(status().isOk()).
			andExpect(view().name("/shop/home"));
		}
	}
}
