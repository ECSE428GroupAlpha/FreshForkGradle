package ca.mcgill.ecse428.freskfork.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.freskfork.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
