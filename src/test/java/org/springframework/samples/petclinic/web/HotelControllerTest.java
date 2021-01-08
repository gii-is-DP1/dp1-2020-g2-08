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
import org.springframework.samples.petclinic.service.ReviewService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=HotelController.class,
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
										   classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration= SecurityConfiguration.class)
public class HotelControllerTest {

	
	@Autowired 
	private HotelController hotelController;
	
	@Autowired
	private MockMvc mockMvc;

	
//	Mockeamos los servicios 
	@MockBean
	private HotelService hotelService;
	
	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private BookingService bookingService;
	
	@MockBean
	private ReviewService reviewService;
	
	
	@WithMockUser(value = "spring")
    @Test
    void testDevolverOwner() throws Exception{
//		TODO
	}

	@WithMockUser(value = "spring")
    @Test
    void testListadoReservas() throws Exception {
//		TODO
		mockMvc.perform(get("/hotel"))
		.andExpect(status().isOk())
		.andExpect(view().name("welcome"));
	}
    
	@WithMockUser(value = "spring")
    @Test
    void testListadoHoteles() throws Exception {
//		TODO
	
		mockMvc.perform(get("/hotel/listadoHoteles"))
		.andExpect(status().isOk())
		.andExpect(view().name("hotel/newHotel"));
		
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testListadoMisReservas() throws Exception {
//		TODO
		
		mockMvc.perform(get("/hotel/myBookings"))
		.andExpect(status().isOk())
		.andExpect(view().name("welcome"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testListadoReservasPorOwner() throws Exception{
//		TODO
		mockMvc.perform(get("/hotel/owner/{ownerId}",1))
		.andExpect(status().isOk())
		.andExpect(view().name("hotel/misReservas"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testCrearHotel() throws Exception{
//		TODO
		mockMvc.perform(get("/hotel/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("hotel/newHotel"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testGuardarBooking() throws Exception {
//		TODO
//		mockMvc.perform(post())
		
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testBorrarHotel() throws Exception{
//		TODO
		mockMvc.perform(get("/hotel/delete/{hotelId}",1))
		.andExpect(status().isOk())
		.andExpect(view().name("hotel/newHotel"));
		
		
	}
	
	
}


































