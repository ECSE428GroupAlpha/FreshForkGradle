package ca.mcgill.ecse428.freskfork.dao;

import java.util.*;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.freskfork.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
    Recipe findByRecipeID(int id);
    List<Recipe> listByDiet(String diet);
}
