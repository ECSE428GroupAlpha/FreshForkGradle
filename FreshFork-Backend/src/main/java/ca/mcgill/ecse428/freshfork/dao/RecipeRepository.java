package ca.mcgill.ecse428.freshfork.dao;

import java.util.*;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.freshfork.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    Recipe findByRecipeID(int id);
    //List<Recipe> listByDiet(String diet);
}
