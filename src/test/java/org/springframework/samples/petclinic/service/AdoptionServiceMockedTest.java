package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.repository.AdoptionRepository;


@ExtendWith(MockitoExtension.class)
public class AdoptionServiceMockedTest {
	
	@Mock
    private AdoptionRepository adoptionRepository;
	
	protected AdoptionService adoptionService;
	
	@BeforeEach
	void setup() {
		adoptionService = new AdoptionService(adoptionRepository);
	}
	
	@Test
	void shouldInsertAdoptionPositivo() {
		Adoption a = new Adoption();
		a.setdate(LocalDate.now());            
		
        Collection<Adoption> adoptions =  new ArrayList<Adoption>();
        adoptions.add(a);
        when(adoptionRepository.findAll()).thenReturn(adoptions);
        
        Collection<Adoption> adoptions1 = (Collection<Adoption>) this.adoptionService.findAll();
        
        assertThat(adoptions1).hasSize(1);
        Adoption adoption = adoptions1.iterator().next();
        assertThat(adoption.getdate()).isEqualTo(LocalDate.now());
	}

}
