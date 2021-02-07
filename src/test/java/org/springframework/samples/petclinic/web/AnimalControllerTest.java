package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Animal;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.AnimalRepository;
import org.springframework.samples.petclinic.repository.ShelterRepository;
import org.springframework.samples.petclinic.service.AnimalService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ShelterService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = AnimalController.class,
includeFilters = {@ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE), 
		@ComponentScan.Filter(value = SexTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE)},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class AnimalControllerTest {
	

	private static final int TEST_ANIMAL_ID = 1;
	private static final int TEST_SHELTER_ID = 1;

	@Autowired
	private AnimalController animalController;
	
	@MockBean
	private ShelterService shelterService;
		
	@MockBean
	private AnimalRepository animalRepo;
	
	@MockBean
	private PetService petService;


	@MockBean
	private AnimalService animalService;
        

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		PetType cat = new PetType();
		cat.setId(3);
		cat.setName("hamster");
		given(this.animalService.findPetTypes()).willReturn(Lists.newArrayList(cat));
		given(this.animalService.findAnimalById(TEST_ANIMAL_ID)).willReturn(new Animal());
	}

	@WithMockUser(value = "spring")
        @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/shelter/animals/{shelterId}/animal/new", TEST_SHELTER_ID)).andExpect(status().isOk())
				.andExpect(view().name("animals/createOrUpdateAnimalForm")).andExpect(model().attributeExists("animal"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/shelter/animals/{shelterId}/animal/new", TEST_SHELTER_ID)
							.with(csrf())
							.param("name", "Betty")
							.param("type", "hamster")
							.param("birthDate", "2015/02/12")
							.param("sex", "Male")
							.param("shelterDate", "2020/02/02"))
				.andExpect(status().isOk())
//				.andExpect(view().name("redirect:/shelter/listadoRefugios"));
				.andExpect(view().name("animals/createOrUpdateAnimalForm"));
	}

	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/shelter/animals/{shelterId}/animal/new",TEST_SHELTER_ID)
							.with(csrf())
							.param("name", "Betty")
							.param("birthDate", "2015/02/12"))
				.andExpect(model().attributeHasErrors("animal"))
				.andExpect(status().isOk())
				.andExpect(view().name("animals/createOrUpdateAnimalForm"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/shelter/animals/{animalId}/edit", TEST_ANIMAL_ID))
				.andExpect(status().isOk()).andExpect(model().attributeExists("animal"))
				.andExpect(view().name("animals/createOrUpdateAnimalForm"));
	}
    
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/shelter/animals/{animalId}/edit", TEST_ANIMAL_ID)
							.with(csrf())
							.param("name", "Betty")
							.param("type", "hamster")
							.param("birthDate", "2015/02/12")
							.param("sex", "Male")
							.param("shelterDate", "2020/02/02"))
				.andExpect(status().isOk())
				.andExpect(view().name("animals/createOrUpdateAnimalForm"));
	}
    
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(post("/shelter/animals/{animalId}/edit", TEST_ANIMAL_ID)
							.with(csrf())
							.param("name", "Betty")
							.param("birthDate", "2015/02/12"))
				.andExpect(model().attributeHasErrors("animal")).andExpect(status().isOk())
				.andExpect(view().name("animals/createOrUpdateAnimalForm"));
	}

}



