package ca.mcgill.ecse428.freshfork.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.freshfork.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
