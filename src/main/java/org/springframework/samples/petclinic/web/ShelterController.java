package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Animal;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Shelter;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ShelterService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/shelter")
public class ShelterController {
	
	@Autowired
	private ShelterService shelterService;
	
	
	@InitBinder("shelter")
	public void initShelterBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ShelterValidator());
	}
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@Autowired
	public ShelterController(ShelterService shelterService) {
		this.shelterService = shelterService;
	}
	
	
	
	@GetMapping(path = "/listadoRefugios")
	public String listadoRefugios(ModelMap modelmap) {
		List<Shelter> refugios = (List<Shelter>) shelterService.findAll();
		
		if(refugios.size() >1) {
			modelmap.addAttribute("refugios", refugios);
			log.info("Se muestra el listado de refugios");
			return "shelter/listadoRefugios";
		} else {
			modelmap.addAttribute("message",
					"No hay refugios disponibles en este momento, crea uno para acceder al listado");
			return crearShelter(modelmap);
		}
	}
	
	@GetMapping(path = "/new")
	public String crearShelter(ModelMap modelmap) {
		Shelter shelter = new Shelter();
		modelmap.addAttribute("shelter", shelter);
		return "shelter/newShelter";
	}
	
	@PostMapping(path = "/new")
	public String guardarShelter(@Valid Shelter shelter, BindingResult result, ModelMap modelmap) {

		if(result.hasErrors()) {
			return "shelter/newShelter";
		}
		modelmap.addAttribute("message", "Refugio creado con éxito!");
		shelterService.save(shelter);
		modelmap.clear();
		String vista = listadoRefugios(modelmap);
		return vista;

	}
	
	@GetMapping(path = "/delete/{shelterId}")
	public String borrarShelter(@PathVariable("shelterId") Integer shelterId, ModelMap modelmap) {
		shelterService.deleteById(shelterId);
		modelmap.addAttribute("message", "Refugio borrado con éxito");
		log.info("Se borra de la bd el refugio con id: "+shelterId);
		return listadoRefugios(modelmap);
	}

}
