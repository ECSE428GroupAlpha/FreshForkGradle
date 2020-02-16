package ca.mcgill.ecse428.freskfork.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.freskfork.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	List<User> findAllByEmail(String email);
	User findByEmail(String email);
	User findByName(String name);
}
