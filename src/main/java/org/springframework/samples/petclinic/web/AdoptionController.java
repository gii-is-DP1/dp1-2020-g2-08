package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
@RequestMapping("/shelter/animals/adoption")
public class AdoptionController {
	@Autowired
	ShelterController shelterController;

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

	@GetMapping(value = "/{animalId}/new")
    public String initAdoptionForm(@PathVariable("animalId") int animalId,ModelMap model,Owner owner) {
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
     * @throws DuplicatedPetNameException 
     * @throws DataAccessException 
     */

	@PostMapping(value = "/{animalId}/new")
    public String processUpdateForm(@Valid Animal animal, BindingResult result,@PathVariable("animalId") Integer animalId, 
            ModelMap model,@PathParam("ownerId") Integer ownerId,Adoption adoption) {
        if (result.hasErrors()) {
            model.put("animal", animal);
            return "adoption/newAdoption";
        }
        else {
        				Pet pet = new Pet();
        				Owner owner;
        				owner = this.ownerService.findOwnerById(ownerId);
                        animal=this.animalService.findAnimalById(animalId);
            BeanUtils.copyProperties(animal, pet,"id", "sex", "state", "description", "imageUrl", "shelter",
                    "shelterDate", "diasEnRefugio");
            			owner.addPet(pet);
            			pet.setBookings(null);
            			pet.setVisits(null);
            			animal.setState("Not available");
            			//Adoption adoption = new Adoption();
//            			adoption.setPet(pet);
//            			//Integer id = adoption.getPet().getId();
//            			adoption.setAnimal(this.animalService.findAnimalById(animalId));
//            			adoption.setOwner(owner);
//            			adoption.setdate(LocalDate.now());
            			
            			
            			adoption.setPet(pet);
            			animal.setDiasEnRefugio(null);
            			//Integer id = adoption.getPet().getId();
            			adoption.setAnimal(animal);
            			adoption.setOwner(owner);
            			adoption.setdate(LocalDate.now());
            			
            			
            			
                    //this.petService.savePet(pet);
					adoptionService.save(adoption);
            return "redirect:/animals";
        }
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
