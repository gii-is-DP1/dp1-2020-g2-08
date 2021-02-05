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
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.repository.AdoptionRepository;

import org.springframework.samples.petclinic.repository.ShelterRepository;
import org.springframework.stereotype.Service;

@Service
public class AdoptionService {

	@Autowired
	private static AdoptionRepository adoptionRepo;

	@Autowired
	private ShelterRepository shelterRepo;

	@Transactional
	public int AdoptionCount() {

		return (int) adoptionRepo.count();
	}

	@Transactional
	public Iterable<Adoption> findAll() {
		return adoptionRepo.findAll();
	}
  
	@Transactional
	public static Adoption findAdoptionById(int AdoptionId) {
		return adoptionRepo.findById(AdoptionId).get();
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
	public List<Adoption> findAdoptionsByShelterId(int shelterId) {
		List<Adoption> lista = new ArrayList<Adoption>();
		lista = (List<Adoption>) adoptionRepo.findAll();
		return lista.stream().filter(x -> x.getShelter().getId().equals(shelterId)).collect(Collectors.toList());
	}

	@Transactional
	public void deleteById(int AdoptionId) {
		adoptionRepo.deleteById(AdoptionId);

	}

	@Transactional
	public void delete(Adoption Adoption) {
		adoptionRepo.delete(Adoption);

	}

	@Transactional
	public void delete(Integer AdoptionId) {
		adoptionRepo.deleteById(AdoptionId);

	}

	@Transactional
	public void save(Adoption Adoption) {
		adoptionRepo.save(Adoption);

	}

	@Transactional
	public void eliminarAdoptionsPorShelter(Integer shelterId) {
		List<Adoption> Adoptions = (List<Adoption>) findAll();
		List<Integer> idAdoption = Adoptions.stream().filter(x -> x.getShelter().getId().equals(shelterId))
				.map(x -> x.getId()).collect(Collectors.toList()); // Lista con los id de los Adoptions a borrar

		if (idAdoption.size() > 0) {
			// Borrado de todos los Adoptions asociados al refugio seleccionado
			for (int i = 0; i < idAdoption.size(); i++) {
				deleteById(idAdoption.get(i));

			}
		}
	}
//
//	public boolean numeroDiasValido(Adoption Adoption) {
//
//		long dias = DAYS.between(Adoption.getStartDate(), Adoption.getEndDate());
//
//		if ((dias < 1) || (dias > 7)) {
//			return false;
//		} else {
//			return true;
//		}
//
//	}
//
//	public boolean fechaValida(Adoption Adoption) {
//		if (Adoption.getStartDate().isBefore(Adoption.getEndDate())) {
//			return true;
//		} else {
//			return false;
//		}
//	}



	

	public List<String> parseaDates(List<String> lista) {
		List<String> res = new ArrayList<String>();

		for (int i = 0; i < lista.size(); i++) {
			res.add(lista.get(i).replace("-", ","));
		}

		return res;
	}

	public List<String> parseaDates2(List<LocalDate> lista) {
		List<String> res = new ArrayList<String>();

		for (int i = 0; i < lista.size(); i++) {
			int dia = lista.get(i).getDayOfMonth();
			String mes = lista.get(i).getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
			int anyo = lista.get(i).getYear();

			res.add(dia + " de " + mes + " del " + anyo);
		}

		return res;
	}
    //   new Date(2020,12,29).getDate() == date.getDate() || new Date( ).getDate() == date.getDate()
	public String restriccionCalendario(List<String> lista) {
		String res ="new Date("+lista.get(0).replace("-",",")+").valueOf() == fecha.valueOf() ";
		
		
		for (int i = 1; i < lista.size(); i++) {
			
			
			
			res = res+" || new Date("+lista.get(i).replace("-",",")+" ).valueOf() == fecha.valueOf()";
		}
		
		
		return res ;
		
	}

}
