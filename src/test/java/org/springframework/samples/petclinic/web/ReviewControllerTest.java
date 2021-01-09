package org.springframework.samples.petclinic.web;


import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ReviewService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ReviewController.class,excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),excludeAutoConfiguration= SecurityConfiguration.class)
public class ReviewControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired 
	private ReviewController reviewController;

	@MockBean 
	BookingController bookingController;
	
	@MockBean
	HotelController hotelController;
	
	
//	Mockeamos los servicios 
	@MockBean
	private HotelService hotelService;
	
	@MockBean
	private BookingService bookingService;
	
	@MockBean
	private PetService petService;
	
	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private ReviewService reviewService;
	
	
	
	
	@WithMockUser(value = "spring")
    @Test
    void crearReviewHotelTest() throws Exception{
//		TODO
		mockMvc.perform(get("/hotel/review"))
		.andExpect(status().isOk())
		.andExpect(view().name("hotel/review"));
//		
	}
	
	
	@WithMockUser(value = "spring")
    @Test
    void borrarReviewTest() throws Exception{
//		TODO
		mockMvc.perform(get("/hotel/review/delete/{reviewId}",1))
		.andExpect(status().isOk())
		.andExpect(view().name("hotel/review/delete/1"));
	}
	
}


































