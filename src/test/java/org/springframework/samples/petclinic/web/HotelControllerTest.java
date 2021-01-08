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

	
	private static final int TEST_HOTEL_ID = 1;
	
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
	
	private Hotel h;
	
	
//	@BeforeEach
//	void setUp() {
//		h = new Hotel();
//		
//		h.setId(TEST_HOTEL_ID);
//		h.setCity("BILBAO");
//		h.setOcupadas(35);
//		h.setAforo(50);
//		
//		given(this.hotelService.findById(TEST_HOTEL_ID)).willReturn(h);
//		
//	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDevolverOwner() {
//		TODO
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testListadoReservas() {
//		TODO
	}
    
	
//	----------------------------------------------------------------------
    
	
	
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
    void testListadoReservasPorOwner() {
//		TODO
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testCrearHotel() {
//		TODO
	}
	
	
	@WithMockUser(value = "spring")
    @Test
    void testGuardarBooking() {
//		TODO
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testBorrarHotel() {
//		TODO
	}
	
	
}


































