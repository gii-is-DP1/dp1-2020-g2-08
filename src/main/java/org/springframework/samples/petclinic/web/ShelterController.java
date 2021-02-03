package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Shelter;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ShelterService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/shelter")
public class ShelterController {
	
	@Autowired
	private ShelterService shelterService;
	
	@Autowired
	private OwnerService ownerService; 
	
	@Autowired
	public ShelterController(ShelterService shelterService, OwnerService ownerService) {
		this.shelterService = shelterService;
		this.ownerService = ownerService;
	}
	
	public void devolverOwner(ModelMap modelmap) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object sesion = auth.getPrincipal();
		UserDetails us = null;
		if (sesion instanceof UserDetails) {
			us = (UserDetails) sesion;
		}

		String res = us.getUsername();

		if (us.getAuthorities().iterator().next().getAuthority().equals("owner")) {

			Owner o = (ownerService.findAllOwners().stream().filter(x -> x.getUser().getUsername().equals(res)))
					.collect(Collectors.toList()).get(0);
			Integer id = o.getId();

			modelmap.addAttribute("ownerId", id);
			modelmap.addAttribute("owner", o);

		} else {
			modelmap.addAttribute("message", "No estas logueado como owner");

		}

	}
	
	
	@GetMapping(path = "/listadoRefugios")
	public String listadoRefugios(ModelMap modelmap) {
		List<Shelter> refugios = (List<Shelter>) shelterService.findAll();
		
		if(refugios.iterator().hasNext()) {
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
	
	@GetMapping(path = "/delete/{shelterId}")
	public String borrarShelter(@PathVariable("shelterId") Integer shelterId, ModelMap modelmap) {
		shelterService.deleteById(shelterId);
		modelmap.addAttribute("message", "Refugio borrado con éxito");
		log.info("Se crea correctamente un nuevo refugio con id: "+shelterId);
		return listadoRefugios(modelmap);
	}

}
