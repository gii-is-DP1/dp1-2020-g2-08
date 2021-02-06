package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;
import org.springframework.samples.petclinic.model.Animal;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.model.SexType;
import org.springframework.samples.petclinic.model.Shelter;
import org.springframework.samples.petclinic.repository.AnimalRepository;
import org.springframework.samples.petclinic.service.AnimalService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ShelterService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedAnimalNameException;
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
@RequestMapping("/shelter/animals")
public class AnimalController {
	 
	private static final String VIEW_ANIMALS_CREATE_OR_UPDATE_FORM = "animals/createOrUpdateAnimalForm";
	@Autowired
	private final AnimalService animalService;
	private final OwnerService ownerService;
	@Autowired
	private final ShelterService shelterService;
	@Autowired
	private final AnimalRepository animalRepo;
	
	
	@Autowired
	public AnimalController(AnimalService animalService, OwnerService ownerService, AnimalRepository animalRepo) {
		this.animalRepo = animalRepo;
		this.shelterService = new ShelterService();
		this.animalService = animalService;
		this.ownerService = ownerService;
	}
	
	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.animalService.findPetTypes();
	}
	
	@ModelAttribute("sexes")
	public Collection<SexType> populateSexTypes() {
		return this.animalService.findSexTypes();
	}
	
//	@ModelAttribute("owner")
//	public Owner findShelter(@PathVariable("shelterId") int ownerId) {
//		return this.ownerService.findOwnerById(ownerId);
//	}
	
	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@InitBinder("animal")
	public void initAnimalBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new AnimalValidator());
	}
	
	
	// LISTADO DE TODOS LOS ANIMALES
		@GetMapping("")
		public String listadoAnimales(ModelMap modelmap) {

			
			
			// Trae todos los refugios
			Iterable<Shelter> shelters = shelterService.findAll();

			if (shelters.iterator().hasNext()) {
				// Mete todos los refugios en el modelmap para mostrarlos en la vista
				modelmap.addAttribute("shelters", shelters);
				if (animalRepo.findAllAnimals().isEmpty()) {
					modelmap.addAttribute("message", "No hay animales disponibles en este momento");
					return "welcome";
				}
				else {
				modelmap.addAttribute("masViejo", animalService.masTiempoEnRefugio());
				}
				// Manda todos los atributos a la vista listaReservas.jsp
				return "animals/listadoAnimales";

			} else {
				modelmap.addAttribute("message", "No hay animales disponibles en este momento");
				return "welcome";
			}

		}
	
	
	// /shelter/1/animal/Sterling
		@GetMapping("{shelterId}/animal/{animalName}")
		public String animalDetails(@PathVariable("shelterId") Integer shelterId, ModelMap model,@PathVariable("animalName") String animalName) {
			Animal animal = shelterService.findAnimalByName(animalName, shelterId);
			model.put("animal", animal);
			return "animals/animalDetails";
		}
		
		
	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap model) {
		Animal animal = new Animal();
		model.put("animal", animal);
		return VIEW_ANIMALS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Animal animal, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("animal", animal);
			return VIEW_ANIMALS_CREATE_OR_UPDATE_FORM;
		}
		else {
			Integer aforoDisponible = animal.getShelter().getAforo() - animal.getShelter().getAnimals().size();
			if(aforoDisponible <=0) {
				model.put("animal", animal);
				model.addAttribute("message", "El aforo del refugio en el que intenta registrar al animal se encuentra completo");
				return VIEW_ANIMALS_CREATE_OR_UPDATE_FORM;
			}
			else {
                    try{ 
                    	this.animalService.saveAnimal(animal);
                    }catch(DuplicatedAnimalNameException ex){
                        result.rejectValue("name", "duplicate", "already exists");
                        return VIEW_ANIMALS_CREATE_OR_UPDATE_FORM;
                    }
                    return "redirect:/shelter/animals";
			}
		}
	}

	@GetMapping(value = "/{animalId}/edit")
	public String initUpdateForm(@PathVariable("animalId") int animalId, ModelMap model) {
		Animal animal = this.animalService.findAnimalById(animalId);
		model.put("animal", animal);
		return VIEW_ANIMALS_CREATE_OR_UPDATE_FORM;
	}

        @PostMapping(value = "/{animalId}/edit")
	public String processUpdateForm(@Valid Animal animal, BindingResult result,@PathVariable("animalId") Integer animalId, 
			ModelMap model) {
		if (result.hasErrors()) {
			model.put("animal", animal);
			return VIEW_ANIMALS_CREATE_OR_UPDATE_FORM;
		}
		else {
                        Animal animalToUpdate=this.animalService.findAnimalById(animalId);
			BeanUtils.copyProperties(animal, animalToUpdate, "id", "state", "shelter", "diasEnRefugio");                                                                                  
                    try {                    
                        this.animalService.saveAnimal(animalToUpdate);                    
                    } catch (DuplicatedAnimalNameException ex) {
                        result.rejectValue("name", "duplicate", "already exists");
                        return VIEW_ANIMALS_CREATE_OR_UPDATE_FORM;
                    }
			return "redirect:/shelter/animals";
		}
	}
        
        @GetMapping(path = "{animalId}/delete")
    	public String borrarAnimal(@PathVariable("animalId") Integer animalId, ModelMap modelmap) {
    		animalService.deleteById(animalId);
    		modelmap.addAttribute("message", "Animal borrado con éxito");
    		return listadoAnimales(modelmap);
    	}



}
