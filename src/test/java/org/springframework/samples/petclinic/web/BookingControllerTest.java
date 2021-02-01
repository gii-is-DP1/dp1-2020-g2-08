package org.springframework.samples.petclinic.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=BookingController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class BookingControllerTest {

	@Autowired 
	private BookingController bookingController;
	
	@MockBean
	private HotelController hotelController;
	@MockBean
	private OwnerService ownerService;
	@MockBean
	private BookingService bookingService;
	@MockBean
	private HotelService hotelService;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@WithMockUser(value = "spring")
    @Test
    void testElegirHotel() throws Exception{
		mockMvc.perform(get("/hotel/booking/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("hotel/newBooking"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testCrearBooking() throws Exception{
		if(hotelService.findAll().iterator().hasNext()) {
			if(ownerService.esOwner()) {
				mockMvc.perform(get("/hotel/booking/new/{hotelId}",1))
				.andExpect(status().isOk())
				.andExpect(view().name("hotel/editBooking"));
			} else {
				mockMvc.perform(get("/hotel/booking/new/{hotelId}",1))
				.andExpect(status().isOk());
			}
		} else {
			mockMvc.perform(get("/hotel/booking/new/{hotelId}",1))
			.andExpect(status().isOk())
			.andExpect(view().name("welcome"));
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testGuargarBooking() throws Exception{
		mockMvc.perform(post("/hotel/booking/new/{hotelId}",1).
				param("ownerId", "1").
				with(csrf())).
		andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testEdit() throws Exception{
		if(ownerService.esOwner()) {
			mockMvc.perform(get("/hotel/booking/edit/{bookingId}",1)).
			andExpect(status().isOk()).
			andExpect(view().name("hotel/editBooking"));
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessUpdateForm() throws Exception{
		if(ownerService.esOwner()) {
			mockMvc.perform(post("/hotel/booking/edit/{bookingId}",1).
					param("ownerId", "1").
					with(csrf())).
			andExpect(status().isOk()).
			andExpect(view().name("hotel/editBooking"));
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testBorrarBooking() throws Exception{
		mockMvc.perform(get("/hotel/booking/delete/{bookingId}/{ownerId}",1,1))
		.andExpect(status().isOk())
		.andExpect(view().name("hotel/booking/delete/1/1"));
	}
	
}


































