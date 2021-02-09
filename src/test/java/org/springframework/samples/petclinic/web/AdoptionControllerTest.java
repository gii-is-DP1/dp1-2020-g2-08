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
import org.springframework.samples.petclinic.repository.AdoptionRepository;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.AnimalService;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ShelterService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=AdoptionController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class AdoptionControllerTest {
	
	
	@MockBean
	private HotelController hotelController;
	@MockBean
	private OwnerService ownerService;
	@MockBean
	private AdoptionService adoptionService;
	@MockBean
	private AnimalService animalService;
	@MockBean
	private PetService petService;
	@MockBean
	private ShelterService shelterService;
	@MockBean
	private AdoptionRepository adoptionRepository;
	@MockBean
	private ShelterController shelterController;
	@MockBean
	private AnimalController animalController;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@WithMockUser(value = "spring")
    @Test
    void testListadoAdoptions() throws Exception {
		if(adoptionService.findAll().iterator().hasNext()) {
			mockMvc.perform(get("adoption/listadoAdopciones"))
			.andExpect(status().isOk())
			.andExpect(view().name("adoption/listadoAdopciones"));
		}
	}
	

	
	@WithMockUser(value = "spring")
    @Test
    void testCrearAdoptionEsOwner() throws Exception{
		if(adoptionService.findAll().iterator().hasNext()) {
			if(ownerService.esOwner()) {
				mockMvc.perform(get("/shelter/animals/adoption/{animalId}/new",1))
				.andExpect(status().isOk())
				.andExpect(view().name("adoption/newAdoption"));
			} 
		} 
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testCrearAdoptionNoEsOwner() throws Exception{
		if(adoptionService.findAll().iterator().hasNext()) {
			if(!ownerService.esOwner()) {
				mockMvc.perform(get("/shelter/animals/adoption/{animalId}/new",1))
				.andExpect(status().isOk());
			}
		}
	}
	

	
	@WithMockUser(value = "spring")
    @Test
    void testGuargarAdoption() throws Exception{
		mockMvc.perform(post("/shelter/animals/adoption/{animalId}/new",1).
				param("ownerId", "1").
				with(csrf())).
		andExpect(status().isOk());
	}
	


}
