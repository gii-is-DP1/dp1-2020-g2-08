package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Animal;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.model.Shelter;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.AdoptionRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.ShelterRepository;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.AnimalService;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ShelterService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedAnimalNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/shelter/animals/adoption")
public class AdoptionController {

	@Autowired
	ShelterController shelterController;
	
	@Autowired
	private AnimalController animalController;

	@Autowired
	private OwnerService ownerService;
	@Autowired
	private PetService petService;
	@Autowired
	private AnimalService animalService;
	@Autowired
	private AdoptionService adoptionService;
	@Autowired
	ShelterService shelterService;
	@Autowired
	private AdoptionRepository adoptionRepository;

	@Autowired
	public AdoptionController(PetService petService, OwnerService ownerService,AnimalService animalService, 
			AdoptionService adoptionService,ShelterService shelterService, AdoptionRepository adoptionRepository) {

		this.ownerService = ownerService;
		this.petService = petService;
		this.animalService=animalService;
		this.adoptionService = adoptionService;
		this.shelterService = shelterService;
		this.adoptionRepository = adoptionRepository;

	}

//
//	@InitBinder("pet")
//	public void initPetBinder(WebDataBinder dataBinder) {
//		dataBinder.setValidator(new PetValidator());
////	}
//	@InitBinder("adoption")
//	public void initAnimalBinder(WebDataBinder dataBinder) {
//		dataBinder.setValidator(new AdoptionValidator());
//	}
//	
//	
//	@GetMapping(value = "/")
//	public String elegirRefugio(ModelMap modelmap) {
//		List<Shelter> shelters = (List<Shelter>) shelterService.findAll();
//		List<Integer> adoptions = new ArrayList<Integer>();
//		List<Integer> adoptionsremainings = new ArrayList<Integer>();
//	
//		//List<Integer> reviews = new ArrayList<Integer>();
//		//List<Integer> valoracionMedia = new ArrayList<Integer>();
//		//int cont = 0;
//		for (int i = 0; i < shelters.size(); i++) {
//			adoptions.add(shelters.get(i).getAnimals().size());
//			adoptionsremainings.add(shelters.get(i).getAforo()-shelters.get(i).getAnimals().size());
//			//reviews.add(hoteles.get(i).getReviews().size());
//			//Set<Review> reviewss = hoteles.get(i).getReviews();
//			//List<Review> rev = reviewss.stream().collect(Collectors.toList());
//			//for (int j = 0; j < rev.size(); j++) {
//			//	cont = cont + rev.get(j).getStars();
////
//			//}
//			//if (reviewss.size() < 1) {
//			//	valoracionMedia.add(0);
//			//	cont = 0;
//			//} else {
//			//	valoracionMedia.add(cont / reviewss.size());
//			//	cont = 0;
//
//			//}
//		}
//
//		modelmap.addAttribute("shelters", shelters);
//		modelmap.addAttribute("numeroAdoptions", adoptions);
//		modelmap.addAttribute("numeroAdoptionsremainings", adoptionsremainings);
//		
//		//modelmap.addAttribute("numeroReviews", reviews);
//		//modelmap.addAttribute("valoracionMedia", valoracionMedia);
//		//return "animals/listadoAnimales";
//		return "adoption/listadoAdopciones";
//	}
	

	@GetMapping(path = "/")
	public String listadoAdopciones(ModelMap modelmap) {
		List<Adoption> adopciones = (List<Adoption>) adoptionService.findAll();
		
		if(adopciones.iterator().hasNext()) {
			modelmap.addAttribute("adopciones", adopciones);
			return "adoption/listadoAdopciones";
		} else {
			modelmap.addAttribute("message",
					"No hay adopciones disponibles en este momento");
			return "adoption/listadoAdopciones";
		}
	}

	@GetMapping(value = "/{animalId}/new")
    public String initAdoptionForm(@PathVariable("animalId") int animalId,ModelMap model) {
		if(ownerService.esOwner()) {
        Animal animal = this.animalService.findAnimalById(animalId);
        Adoption adoption = new Adoption();
        adoption.setAnimal(animal);
        model.put("animal", animal);
        model.put("animalId", animal.getId());
        model.put("adoption", adoption);
        return "adoption/newAdoption";
		}
		else {
			model.put("message", "Para realizar una adopción, debe estar logueado como owner");
			return "shelter/listadoAnimales";
		}
    }

	@PostMapping(value = "/{animalId}/new")
    public String processUpdateForm(Animal animal,@PathVariable("animalId") Integer animalId, BindingResult result, 
            ModelMap model,@Valid Adoption adoption) {
		
		String nombre = animal.getName();
		

		if (result.hasErrors()) {
            model.put("animal", animal);
            return "adoption/newAdoption";
        }
        else {
        	if(!StringUtils.hasLength(nombre) || nombre.length()>50 || nombre.length()<3) {
        		model.addAttribute("message", "name is required and between 3 and 50 characters");
        		return "adoption/newAdoption";
        	}
        	 Integer ownerId = ownerService.devolverOwnerId();
    	     Owner owner = ownerService.findOwnerById(ownerId);
        	Pet pet = new Pet();
        	pet.setName(animal.getName());
        	  Animal animalToAdopt=this.animalService.findAnimalById(animalId);
        	adoption.setAnimal(animalToAdopt);
        	pet.setBirthDate(animalToAdopt.getBirthDate());
        	pet.setType(animalToAdopt.getType());
        	pet.setOwner(owner);
        	adoption.setOwner(pet.getOwner());
        	adoption.setdate(LocalDate.now());
        	
        	Boolean repetido = adoptionService.adoptionsToList().stream().anyMatch(x->x.getAnimal().getId().equals(animalId));
        	
        	if(repetido == true) {
        		model.addAttribute("message", "the same adoption cannot be repeated");
        		return animalController.listadoAnimales(model);
        	}
        	
        	//El máximo mensual de adopciones por owner es 5
        	List<Adoption> adopcionesOwner = adoptionService.adoptionsToList().stream()
        			.filter(x->x.getOwner().equals(owner)).collect(Collectors.toList());
        	Integer cantidad = (int) adopcionesOwner.stream().filter(x->x.getdate().getMonth()
        					.equals(LocalDate.now().getMonth())).count();
        	if(cantidad >= 5) {
        		model.addAttribute("message", "You have exceeded the monthly limit of adoptions.");
        		return "adoption/newAdoption";
        	}
        	
        	
        	
        	try{ 
            	log.info("Se ha añadido el animal al refugio");
            	this.petService.savePet(pet);
            }catch(DuplicatedPetNameException ex){
                result.rejectValue("name", "duplicate", "already exists");
                return "adoption/newAdoption";
            }
        	
        	adoption.setPet(petService.findPetById(pet.getId()));
        	animalToAdopt.setState("Not avaible");
        	animalToAdopt.setShelter(shelterService.findById(4));
        	this.animalService.save(animalToAdopt);
        	this.adoptionService.save(adoption);
        	return animalController.listadoAnimales(model);
        }
    }



}
