package ca.mcgill.ecse428.freskfork.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.freskfork.model.Users;

public interface UsersRepository extends CrudRepository<Users, Integer> {
	List<Users> findAllByEmail(String email);
	Users findByEmail(String email);
	Users findByName(String name);
	Users findByUID(int id);
}