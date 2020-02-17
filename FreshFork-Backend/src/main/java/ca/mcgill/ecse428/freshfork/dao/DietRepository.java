package ca.mcgill.ecse428.freshfork.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.freshfork.model.Diet;


public interface DietRepository extends CrudRepository<Diet, String> {
	List<Diet> findAllByName(String name);
	Diet findByName(String name);
	List<Diet> findAll();
}
