package org.springframework.samples.petclinic.web;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ReviewService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=HotelController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class HotelControllerTest {

	@Autowired 
	private HotelController hotelController;
	
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
	
	@Autowired
	private MockMvc mockMvc;
	

	@WithMockUser(value = "spring")
    @Test
    void testListadoReservas() throws Exception {
		if(hotelService.findAll().iterator().hasNext()) {
			mockMvc.perform(get("/hotel"))
			.andExpect(status().isOk())
			.andExpect(view().name("hotel/listaReservas"));
		} 
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testListadoReservasNoHotel() throws Exception {
		if(!hotelService.findAll().iterator().hasNext()) {
			mockMvc.perform(get("/hotel"))
			.andExpect(status().isOk())
			.andExpect(view().name("welcome"));
		}
	}
    
	@WithMockUser(value = "spring")
    @Test
    void testListadoHoteles() throws Exception {
		if(hotelService.findAll().iterator().hasNext()) {
			mockMvc.perform(get("/hotel/listadoHoteles"))
			.andExpect(status().isOk())
			.andExpect(view().name("hotel/listadoHoteles"));
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testListadoHotelesNoHotel() throws Exception {
		if(!hotelService.findAll().iterator().hasNext()) {
			mockMvc.perform(get("/hotel/listadoHoteles"))
			.andExpect(status().isOk())
			.andExpect(view().name("hotel/newHotel"));
		}
	}

	@WithMockUser(value = "spring")
    @Test
    void testListadoMisReservasEsOwner() throws Exception {
		if(ownerService.esOwner()) {
			mockMvc.perform(get("/hotel/myBookings"))
			.andExpect(status().isOk())
			.andExpect(view().name("hotel/misReservas"));
		} 
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testListadoMisReservasHotel() throws Exception {
		if(!ownerService.esOwner()) {
			if(hotelService.findAll().iterator().hasNext()) {
				mockMvc.perform(get("/hotel/myBookings"))
				.andExpect(status().isOk())
				.andExpect(view().name("hotel/listaReservas"));
			} 
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testListadoMisReservasNoHotel() throws Exception {
		if(!ownerService.esOwner()) {
			if(hotelService.findAll().iterator().hasNext()) {
				mockMvc.perform(get("/hotel/myBookings"))
				.andExpect(status().isOk())
				.andExpect(view().name("welcome"));
			}
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testListadoReservasPorOwner() throws Exception {
			mockMvc.perform(get("/hotel/owner/{ownerId}",1))
			.andExpect(status().isOk())
			.andExpect(view().name("hotel/misReservas"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testCrearHotel() throws Exception{
		mockMvc.perform(get("/hotel/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("hotel/newHotel"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testGuardarBooking() throws Exception {
		if(hotelService.findAll().iterator().hasNext()) {
			mockMvc.perform(post("/hotel/save").
					with(csrf())).
			andExpect(status().isOk()).
			andExpect(view().name("hotel/listadoHoteles"));
		} 
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testGuardarBookingNoHotel() throws Exception {
		if(!hotelService.findAll().iterator().hasNext()) {
			mockMvc.perform(post("/hotel/save").
					with(csrf())).
			andExpect(status().isOk()).
			andExpect(view().name("hotel/newHotel"));
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testBorrarHotel() throws Exception{
		if(hotelService.findAll().iterator().hasNext()) {
			mockMvc.perform(get("/hotel/delete/{hotelId}",1))
			.andExpect(status().isOk())
			.andExpect(view().name("hotel/listadoHoteles"));
		} 
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testBorrarHotelNoHotel() throws Exception{
		if(!hotelService.findAll().iterator().hasNext()) {
			mockMvc.perform(get("/hotel/delete/{hotelId}",1))
			.andExpect(status().isOk())
			.andExpect(view().name("hotel/newHotel"));
		}
	}
		
}
