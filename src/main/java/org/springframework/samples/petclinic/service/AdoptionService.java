package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.repository.AdoptionRepository;

import org.springframework.samples.petclinic.repository.ShelterRepository;
import org.springframework.stereotype.Service;

@Service
public class AdoptionService {

	@Autowired
	private AdoptionRepository adoptionRepo;

	
	public AdoptionService(AdoptionRepository adoptionRepository) {
		this.adoptionRepo = adoptionRepository;
	}
	@Transactional
	public int AdoptionCount() {

		return (int) adoptionRepo.count();
	}

	@Transactional
	public Iterable<Adoption> findAll() {
		return adoptionRepo.findAll();
	}
	
	@Transactional
	public List<Adoption> adoptionsToList() throws DataAccessException {
		return (List<Adoption>) adoptionRepo.findAll();
	}
  
	@Transactional
	public Adoption findAdoptionById(int AdoptionId) {
		return adoptionRepo.findById(AdoptionId).get();
	}

	@Transactional
	public List<Adoption> findAdoptionsByPetId(int petId) {
		List<Adoption> lista = new ArrayList<Adoption>();
		lista = (List<Adoption>) adoptionRepo.findAll();
		return lista.stream().filter(x -> x.getOwner().getId().equals(petId)).collect(Collectors.toList());
	}
	
	@Transactional
	public List<Adoption> findAdoptionsByAnimalId(int animalId) {
		List<Adoption> lista = new ArrayList<Adoption>();
		lista = (List<Adoption>) adoptionRepo.findAll();
		return lista.stream().filter(x -> x.getOwner().getId().equals(animalId)).collect(Collectors.toList());
	}
	
	@Transactional
	public List<Adoption> findAdoptionsByOwnerId(int ownerId) {
		List<Adoption> lista = new ArrayList<Adoption>();
		lista = (List<Adoption>) adoptionRepo.findAll();
		return lista.stream().filter(x -> x.getOwner().getId().equals(ownerId)).collect(Collectors.toList());
	}
	
	@Transactional
	public Integer numeroAdoptionsPorOwner(int ownerId) {
		List<Adoption> lista = new ArrayList<Adoption>();
		lista = (List<Adoption>) adoptionRepo.findAll();
		return lista.stream().filter(x -> x.getOwner().getId().equals(ownerId)).collect(Collectors.toList()).size();
	}


	@Transactional
	public void save(Adoption Adoption) {
		adoptionRepo.save(Adoption);

	}

}
