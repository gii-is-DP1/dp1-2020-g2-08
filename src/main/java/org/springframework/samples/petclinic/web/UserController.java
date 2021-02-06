/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class UserController {

	private static final String VIEWS_OWNER_CREATE_FORM = "users/createOwnerForm";

	private final OwnerService ownerService;
	
	private final ClientService clientService;
	
	private final UserService userService;
	
	private final AuthoritiesService authoritiesService;

	@Autowired
	public UserController(OwnerService clinicService, ClientService clientService, AuthoritiesService authoritiesService, UserService userService) {
		this.ownerService = clinicService;
		this.clientService = clientService;
		this.authoritiesService = authoritiesService;
		this.userService = userService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/users/new")
	public String initCreationForm(Map<String, Object> model) {
		Owner owner = new Owner();
		model.put("owner", owner);
		return VIEWS_OWNER_CREATE_FORM;
	}

	@PostMapping(value = "/users/new")
	public String processCreationForm(@Valid Owner owner, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_FORM;
		}
		else {
			//creating owner, user, and authority
			this.ownerService.saveOwner(owner);
			return "redirect:/";
		}
	}
	
	@InitBinder("client")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ClientValidator());
	}

	
	@GetMapping(value = "/users/new/client")
	public String initCreationClientForm(Map<String, Object> model) {
		Client client = new Client();
		model.put("client", client);
		return "users/createClientForm";
	}

	@PostMapping(value = "/users/new/client")
	public String processCreationClientForm(@Valid Client client,BindingResult result, ModelMap modelmap) {
		
		
		if (result.hasErrors()) {
			
			modelmap.addAttribute("client", client);
			return "users/createClientForm";
		}
		else {
			if(!userService.findUser(client.getNameuser()).get().getUsername().isEmpty()) {
				modelmap.addAttribute("message", "This user is already exists");
				return "users/createClientForm";
			}
			this.clientService.saveClient(client);
			log.info("Se ha creado el usuario correctamente");

			return "redirect:/";
		}
	}

}
