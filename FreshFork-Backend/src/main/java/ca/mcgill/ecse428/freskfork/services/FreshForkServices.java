package ca.mcgill.ecse428.freskfork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse428.freskfork.dao.DietRepository;
import ca.mcgill.ecse428.freskfork.dao.IngredientRepository;
import ca.mcgill.ecse428.freskfork.dao.IngredientUsageRepository;
import ca.mcgill.ecse428.freskfork.dao.RecipeRepository;
import ca.mcgill.ecse428.freskfork.dao.UserRepository;
import ca.mcgill.ecse428.freskfork.model.Recipe;
import ca.mcgill.ecse428.freskfork.model.User;

import java.util.*;

@Service
public class FreshForkServices {
	@Autowired
	DietRepository dietRepository;
	
	@Autowired
	IngredientUsageRepository ingredientUsageRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	RecipeRepository recipeRepository;

	@Autowired
	UserRepository userRepository;


	// USER METHODS

	@Transactional
	public User createUser(String name, String email, String password, boolean isPro) {
		User user = new User();
		
		if(userRepository.findAllByEmail(email).size() == 1) {
			throw new IllegalArgumentException("Email is already taken.");
		}
		else {
			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);
			user.setIsPro(isPro);
		}
		
		return userRepository.save(user);
	}

	// RECIPE METHODS

	@Transactional
	public Recipe createRecipe(int author, String recipeSteps, String rating) {
		Recipe recipe = new Recipe();
		User user = userRepository.findByUID(author);
		//Checks if user is pro first, otherwise return null
		if(user.isIsPro()) {
			recipe.setAuthor(user);
			recipe.setRecipeSteps(recipeSteps);
			recipe.setRating(rating);
			return recipeRepository.save(recipe);
		}
		else {
			throw new IllegalArgumentException("User is not a professional");
		}
	}

	@Transactional
	public String deleteRecipe(int recipeID) {
		String recipeName;
		
		Recipe recipeToDelete = recipeRepository.findByRecipeID(recipeID);
		if(recipeToDelete == null) {
			//Recipe could not be found, returning null
			return null;
		}
		else {
			//Store recipeName in new string before deleting recipe
			recipeName = new String(recipeToDelete.getName());
			recipeRepository.delete(recipeToDelete);
		}
		
		return recipeName;
	}

	/*@Transactional
	public List<Recipe> filterRecipeByDiet(String dietName) {
		return recipeRepository.listByDiet(dietName);
	}*/

	// AUTHENTICATION

	@Transactional
	public boolean authenticateUser(String email, String password) {
		User user = userRepository.findByEmail(email);
		
		if(user == null) {
			throw new IllegalArgumentException("Account with given email does not exist.");
		}
		else {
			if(password.equals(user.getPassword())) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}
