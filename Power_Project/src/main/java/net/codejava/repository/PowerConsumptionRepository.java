package net.codejava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import net.codejava.model.PowerConsumption;

public interface PowerConsumptionRepository extends CrudRepository<PowerConsumption, Long> {

	@Query("SELECT u FROM PowerConsumption u WHERE u.user_id = :id")
	public List<PowerConsumption> getAllFromUser(@Param("id") Long id);

}
