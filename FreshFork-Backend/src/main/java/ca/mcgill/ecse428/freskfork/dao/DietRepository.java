package ca.mcgill.ecse428.freskfork.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.freskfork.model.Diet;


public interface DietRepository extends CrudRepository<Diet, String> {
	List<Diet> findAllByName(String name);
	Diet findByName(String name);
	List<Diet> findAll();
}
