package net.codejava.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import net.codejava.model.MyUserDetails;
import net.codejava.model.PowerConsumption;
import net.codejava.repository.PowerConsumptionRepository;

@Service
public class PowerConsumptionService {
	@Autowired 
	PowerConsumptionRepository repo;
	
	public List<PowerConsumption> getAll(){
		return (List<PowerConsumption>) repo.findAll();
	}
	public List<PowerConsumption> getAllFromUser(Long id){
		return repo.getAllFromUser(id);
	}
	
	public void save(PowerConsumption powerConsumption) {
		repo.save(powerConsumption);
	}
	public PowerConsumption get(Long id) {
		return repo.findById(id).get();
	}
	public void delete(Long id) {
		repo.deleteById(id);
	}
}


