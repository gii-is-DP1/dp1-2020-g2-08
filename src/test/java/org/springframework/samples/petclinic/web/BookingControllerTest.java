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

@WebMvcTest(controllers=BookingController.class,excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),excludeAutoConfiguration= SecurityConfiguration.class)
public class BookingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired 
	private BookingController bookingController;
	
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
    void elegirHotelTest() throws Exception{
//		TODO
		mockMvc.perform(get("/hotel/booking/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("hotel/newBooking"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void crearBookingTest() throws Exception{
//		TODO
		mockMvc.perform(get("/hotel/booking/new/{hotelId}",1))
		.andExpect(status().isOk())
		.andExpect(view().name("welcome"));
		
	}
	
	@WithMockUser(value = "spring")
    @Test
    void guardarBookingTest() throws Exception{
//		TODO
		
	}
	
	@WithMockUser(value = "spring")
    @Test
    void editTest() throws Exception{
//		TODO
		mockMvc.perform(get("/hotel/booking/edit/{bookingId}",1))
		.andExpect(status().isOk())
		.andExpect(view().name("exception"));
		
	}
	
	@WithMockUser(value = "spring")
    @Test
    void processUpdateForm() throws Exception{
//		TODO
		
	}
	
	@WithMockUser(value = "spring")
    @Test
    void borrarBookingTest() throws Exception{
//		TODO
//		mockMvc.perform(get("/hotel/booking/delete/{bookingId}/{ownerId}",1,1))
//		.andExpect(status().isOk())
//		.andExpect(view().)
	}
	

	
	
}


































