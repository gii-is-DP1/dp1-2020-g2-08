package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Animal;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.model.Shelter;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.AnimalService;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ShelterService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shelter/adoption/")
public class AdoptionController {
	@Autowired
	ShelterController shelterController;

	
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
	public AdoptionController(PetService petService, OwnerService ownerService,AnimalService animalService, 
			AdoptionService adoptionService,ShelterService shelterService) {

		this.ownerService = ownerService;
		this.petService = petService;
		this.animalService=animalService;
		this.adoptionService = adoptionService;
		this.shelterService = shelterService;

	}
	/*
	@ModelAttribute("shelters")
	public Iterable<Shelter> findShelter() {
		return this.shelterService.findAll();
	}

	@InitBinder("adoption")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new BookingValidator());
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	 	*/
	
	
	@GetMapping(value = "/")
	public String elegirRefugio(ModelMap modelmap) {
		List<Shelter> shelters = (List<Shelter>) shelterService.findAll();
		List<Integer> adoptions = new ArrayList<Integer>();
		List<Integer> adoptionsremainings = new ArrayList<Integer>();
	
		//List<Integer> reviews = new ArrayList<Integer>();
		//List<Integer> valoracionMedia = new ArrayList<Integer>();
		//int cont = 0;
		for (int i = 0; i < shelters.size(); i++) {
			adoptions.add(shelters.get(i).getAnimals().size());
			adoptionsremainings.add(shelters.get(i).getAforo()-shelters.get(i).getAnimals().size());
			//reviews.add(hoteles.get(i).getReviews().size());
			//Set<Review> reviewss = hoteles.get(i).getReviews();
			//List<Review> rev = reviewss.stream().collect(Collectors.toList());
			//for (int j = 0; j < rev.size(); j++) {
			//	cont = cont + rev.get(j).getStars();
//
			//}
			//if (reviewss.size() < 1) {
			//	valoracionMedia.add(0);
			//	cont = 0;
			//} else {
			//	valoracionMedia.add(cont / reviewss.size());
			//	cont = 0;

			//}
		}

		modelmap.addAttribute("shelters", shelters);
		modelmap.addAttribute("numeroAdoptions", adoptions);
		modelmap.addAttribute("numeroAdoptionsremainings", adoptionsremainings);
		
		//modelmap.addAttribute("numeroReviews", reviews);
		//modelmap.addAttribute("valoracionMedia", valoracionMedia);
		//return "animals/listadoAnimales";
		return "adoption/listadoAdopciones";
	}
/*	
	@GetMapping(value = "/new")
	public String initCreationForm(Owner owner, ModelMap model) {
		//Animal animal = this.animalService.findAnimalById(animalId);
		//this.animalService.save(animal);

		Pet pet = new Pet();
		model.put("pet", pet);
		//model.put("animal", animal);
		
		return "adoption/newAdoption";
	}

	@PostMapping(value = "/new")
	public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result,
			ModelMap model) {		
		if (result.hasErrors()) {
			//model.put("animal", animal);
			model.put("pet", pet);
			return "redirect:/animals"; 
		}
		else {
                    try{ 
                    	//Pet pet = new Pet();
                    	//BeanUtils.copyProperties(animal, pet,"birthDate","type");
                		//pet.setName(animal.getName());
                		//pet.setBirthDate(animal.getBirthDate());
                		//pet.setId(animal.getId());
                		//pet.setType(animal.getType());
                		//animal.setState("Not avaiable");
                		Adoption adoption = new Adoption();
                		adoption.setPet(pet);
                		//adoption.setshelter(animal.getShelter());
                		adoption.setOwner(owner);
                		adoption.setStartDate(LocalDate.now());
                		//adoption.setId(1);
                		owner.addPet(pet);
                		//model.put("pet", pet);
                    	this.petService.savePet(pet);
                    }catch(DuplicatedPetNameException ex){
                        result.rejectValue("name", "duplicate", "already exists");
                        return "adoption/newAdoption";
                    }
                    return "redirect:/owners/{ownerId}";
		}
	}

	@GetMapping(value = "/new")
	public String initCreationForm(Owner owner, ModelMap model) {
		Pet pet = new Pet();
		model.put("pet", pet);
		return "adoption/newAdoption";
	}
	@GetMapping(value = "/new")
	public String initCreationForm(Owner owner,ModelMap model) {
		Animal animal = new Animal();
		model.put("animal", animal);
		return "adoption/newAdoption";

	
	@GetMapping(value = "/new")
	public String initCreationForm(@PathVariable("petId") int petId,Owner owner, ModelMap model) {
		//Pet pet = this.petService.findPetById(petId)
		//Animal animal = this.animalService.findAnimalById(petId);
		//Animal animal = new Animal();
		//model.put("animal", animal);
		Pet pet = new Pet();
		model.put("pet", pet);
		return "adoption/newAdoption";
	}
/*
 * 
 */
	public List<Visit> cogevisit(ModelMap model) {
		List<Visit> v = petService.findPetById(0).getVisits();
		return v;
	}
	/* Version ultima
	@GetMapping(value = "/new")
	public String initCreationForm(Owner owner,ModelMap model) {
		
		Animal animal = this.animalService.findAnimalById(1);
		//Animal animal = new Animal();
		model.put("animal", animal);
		return "adoption/newAdoption";
	}
	*/
	@GetMapping(value = "/new")
    public String initAdoptionForm(@PathVariable("animalId") int animalId, ModelMap model) {
        Animal animal = this.animalService.findAnimalById(animalId);
        model.put("animal", animal);
        return "adoption/newAdoption";
    }
	
	

    /**
     *
     * @param animal
     * @param result
     * @param petId
     * @param model
     * @param owner
     * @param model
     * @return
     */
    @PostMapping(value = "/new")
	public String processUpdateForm(Owner owner, @Valid Animal animal, BindingResult result, ModelMap model) {
        	//Animal animal = new Animal();
        	//BeanUtils.copyProperties(animal, pet,"birthDate","type");
    		Pet pet = new Pet();
			animal = this.animalService.findAnimalById(animal.getId());
			owner = this.ownerService.findOwnerById(owner.getId());
    		//model.put("pet", pet);
    		
    		List<Visit> v = animal.getVisits();
    		//Collection<Visit> v1 = petService.findVisitsByPetId(1);
    		owner.addPet(pet);
    		pet.setBookings(owner.getBookings());
    		pet.setVisits(animal.getVisitsInternal());
//    		for(Visit x:v1) {
//    			pet.addVisit(x);
//    		}
    		//pet.setVisitsInternal(v);
    		if (animal.visits == null) {
    			pet.visits = new HashSet<>();
    		}
    		BeanUtils.copyProperties(animal, pet,"descripcion","imageUrl"
    				,"diasEnRefugio","sex","shelter","shelterDate","state");
    		pet.setId(18);
    		try {
				this.petService.savePet2(pet);
    			//PetRepository.save()
			} catch(DuplicatedPetNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
                return "adoption/newAdoption";
				
			}
			return "redirect:/animals";
		
	}

	
	public void creaModelMap(Hotel hotel, Owner owner, int ownerId, List<Pet> pets, Booking booking,
			ModelMap modelmap) {
		modelmap.put("hotel", hotel);
		modelmap.addAttribute("owner", owner);
		modelmap.addAttribute("ownerId", ownerId);
		modelmap.put("hotelId", hotel.getId());

		modelmap.put("pets", pets);
		modelmap.put("booking", booking);
	}



}
