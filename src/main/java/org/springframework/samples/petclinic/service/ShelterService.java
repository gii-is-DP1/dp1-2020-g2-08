package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Animal;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Shelter;
import org.springframework.samples.petclinic.repository.AnimalRepository;
import org.springframework.samples.petclinic.repository.ShelterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShelterService {
	@Autowired
	private ShelterRepository shelterRepo;
	@Autowired
	private AnimalService animalService;
	@Transactional
	public int shelterCount() {
		return (int) shelterRepo.count();
	}
	@Transactional
	public Iterable<Shelter> findAll() {
		return shelterRepo.findAll();
	}
	
	@Transactional
	public Shelter findById(Integer shelterId){
		 return shelterRepo.findById(shelterId).get();
	 }
	
	@Transactional
	public void save(Shelter shelter) {
		shelterRepo.save(shelter);
		
	}
	
	@Transactional
	public void delete(Shelter shelter) {	
		animalService.eliminarAminalesPorRefugio(shelter.getId());
			shelterRepo.delete(shelter);	
	}
	
	
	public void deleteById(Integer shelterId) {
		
		animalService.eliminarAminalesPorRefugio(shelterId);
		shelterRepo.deleteById(shelterId);
		
	}
	public Integer numeroAnimales(int shelterId) {
		
		Integer num = shelterRepo.findById(shelterId).get().getAnimals().size();
		
		return num;
	}
	
	public Animal findAnimalByName(String name, Integer shelterId) {
		
		return findById(shelterId).getAnimals().stream().filter(x->x.getName().equalsIgnoreCase(name)).findFirst().get();
		
		
	}
	
}
