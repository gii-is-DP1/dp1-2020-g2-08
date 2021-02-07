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
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.ReviewService;
import org.springframework.samples.petclinic.service.ShelterService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ShelterController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class ShelterControllerTest {
	
	@Autowired 
	private ShelterController shelterController;
	
	@MockBean
	private ShelterService shelterService;

	
	@Autowired
	private MockMvc mockMvc;
	

	
    
	@WithMockUser(value = "spring")
    @Test
    void testListadoRefugios() throws Exception {
		if(shelterService.findAll().iterator().hasNext()) {
			mockMvc.perform(get("/shelter/listadoRefugios"))
			.andExpect(status().isOk())
			.andExpect(view().name("shelter/listadoRefugios"));
		}
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testListadoRefugiosNoRefugio() throws Exception {
		if(!shelterService.findAll().iterator().hasNext()) {
			mockMvc.perform(get("/shelter/listadoRefugios"))
			.andExpect(status().isOk())
			.andExpect(view().name("shelter/newShelter"));
		}
	}


	
	@WithMockUser(value = "spring")
    @Test
    void testCrearRefugio() throws Exception{
		mockMvc.perform(get("/shelter/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("shelter/newShelter"));
	}
	
	
	@WithMockUser(value = "spring")
    @Test
    void testBorrarShelter() throws Exception{
		if(shelterService.findAll().iterator().hasNext()) {
			mockMvc.perform(get("/shelter/delete/{shelterId}",1))
			.andExpect(status().isOk())
			.andExpect(view().name("shelter/listadoRefugios"));
		} 
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testBorrarHotelNoHotel() throws Exception{
		if(!shelterService.findAll().iterator().hasNext()) {
			mockMvc.perform(get("/shelter/delete/{shelterId}",1))
			.andExpect(status().isOk())
			.andExpect(view().name("shelter/newShelter"));
		}
	}
		

}
