package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ReviewService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ReviewController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class ReviewControllerTest {


	@Autowired 
	private ReviewController reviewController;
	
	@MockBean
	private HotelController hotelController;
	@MockBean
	private HotelService hotelService;
	@MockBean
	private OwnerService ownerService;
	@MockBean
	private UserService userService;
	@MockBean
	private ReviewService reviewService;
	
	@Autowired
	private MockMvc mockMvc;

	
	@WithMockUser(value = "spring")
    @Test
    void testCrearReviewHotelEsOwner() throws Exception{
		if(ownerService.esOwner()) {
			mockMvc.perform(get("/hotel/review")).
			andExpect(status().isOk()).
			andExpect(view().name("reviews/newReview"));
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testCrearReviewHotel() throws Exception{
		if(hotelService.findAll().iterator().hasNext()) {
			mockMvc.perform(get("/hotel/review")).
			andExpect(status().isOk()).
			andExpect(view().name("hotel/listaReservas"));
		} 
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testCrearReviewHotelNoHotel() throws Exception{
		if(!hotelService.findAll().iterator().hasNext()) {
			mockMvc.perform(get("/hotel/review")).
			andExpect(status().isOk()).
			andExpect(view().name("hotel/review"));
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testGuardarReviewEsOwner() throws Exception {
		if(ownerService.esOwner()) {
			mockMvc.perform(post("/hotel/review/saveReview/{ownerId}",1).
					with(csrf())).
			andExpect(status().isOk()).
			andExpect(model().attributeHasErrors("owner")).
			andExpect(view().name("reviews/newReview"));
		} 
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testGuardarReview() throws Exception {
		if(hotelService.findAll().iterator().hasNext()) {
			mockMvc.perform(post("/hotel/review/saveReview/{ownerId}",1).
					with(csrf())).
			andExpect(status().isOk()).
			andExpect(view().name("hotel/listaReservas"));
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testGuardarReviewNoHotel() throws Exception {
		if(!hotelService.findAll().iterator().hasNext()) {
			mockMvc.perform(post("/hotel/review/saveReview/{ownerId}",1).
					with(csrf())).
			andExpect(status().isOk());
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testBorrarReviewHotel() throws Exception{
		if(hotelService.findAll().iterator().hasNext()) {
			mockMvc.perform(get("/hotel/review/delete/{reviewId}",1)).
			andExpect(status().isOk()).
			andExpect(view().name("hotel/listaReservas"));
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testBorrarReviewNoHotel() throws Exception{
		if(!hotelService.findAll().iterator().hasNext()) {
			mockMvc.perform(get("/hotel/review/delete/{reviewId}",1)).
			andExpect(status().isOk()).
			andExpect(view().name("hotel/review/delete/1"));
		}
	}
	
}
