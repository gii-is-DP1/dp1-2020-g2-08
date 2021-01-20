package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Animal;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.AnimalService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ShelterService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.bytebuddy.description.modifier.Ownership;

@Controller
@RequestMapping("/shelter/{shelterId}")
public class AnimalController {
	
	private static final String VIEW_ANIMALS_CREATE_OR_UPDATE_FORM = "animals/createOrUpdateAnimalForm";
	private final AnimalService animalService;
	private final OwnerService ownerService;
	
	@Autowired
	public AnimalController(AnimalService animalService, OwnerService ownerService) {
		this.animalService = animalService;
		this.ownerService = ownerService;
	}
	
	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.animalService.findPetTypes();
	}
	
	@ModelAttribute("owner")
	public Owner findShelter(@PathVariable("shelterId") int ownerId) {
		return this.ownerService.findOwnerById(ownerId);
	}
	
	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@InitBinder("animal")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new AnimalValidator());
	}
	
	@GetMapping(value = "/animals/new")
	public String initCreationForm(Owner owner, ModelMap model) {
		Animal animal = new Animal();
		model.put("animal", animal);
		return VIEW_ANIMALS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/animals/new")
	public String processCreationForm(Owner owner, @Valid Animal animal, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("animal", animal);
			return VIEW_ANIMALS_CREATE_OR_UPDATE_FORM;
		}
		else {
                    try{ 
                    	this.animalService.saveAnimal(animal);
                    }catch(DuplicatedPetNameException ex){
                        result.rejectValue("name", "duplicate", "already exists");
                        return VIEW_ANIMALS_CREATE_OR_UPDATE_FORM;
                    }
                    return "redirect:/owners/{ownerId}";
		}
	}

	@GetMapping(value = "/animals/{animalId}/edit")
	public String initUpdateForm(@PathVariable("animalId") int animalId, ModelMap model) {
		Animal animal = this.animalService.findAnimalById(animalId);
		model.put("animal", animal);
		return VIEW_ANIMALS_CREATE_OR_UPDATE_FORM;
	}

    /**
     *
     * @param pet
     * @param result
     * @param petId
     * @param model
     * @param owner
     * @param model
     * @return
     */
        @PostMapping(value = "/animals/{animalId}/edit")
	public String processUpdateForm(@Valid Animal animal, BindingResult result, Owner owner,@PathVariable("animalId") int animalId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("animal", animal);
			return VIEW_ANIMALS_CREATE_OR_UPDATE_FORM;
		}
		else {
                        Animal animalToUpdate=this.animalService.findAnimalById(animalId);
			BeanUtils.copyProperties(animal, animalToUpdate, "id");                                                                                  
                    try {                    
                        this.animalService.saveAnimal(animalToUpdate);                    
                    } catch (DuplicatedPetNameException ex) {
                        result.rejectValue("name", "duplicate", "already exists");
                        return VIEW_ANIMALS_CREATE_OR_UPDATE_FORM;
                    }
			return "redirect:/owners/{ownerId}";
		}
	}


}
