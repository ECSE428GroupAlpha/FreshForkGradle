package ca.mcgill.ecse428.freskfork.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.freskfork.model.Diet;


public interface DietRepository extends CrudRepository<Diet, String> {
	Diet findByName(String name);
}
