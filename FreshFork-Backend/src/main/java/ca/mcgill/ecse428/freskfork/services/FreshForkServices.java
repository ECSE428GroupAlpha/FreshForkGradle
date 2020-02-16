package ca.mcgill.ecse428.freskfork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse428.freskfork.dao.*;
import ca.mcgill.ecse428.freskfork.model.*;

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
	UsersRepository usersRepository;


	// Users METHODS

	@Transactional
	public Users createUser(String name, String email, String password, boolean isPro) {
		Users Users = new Users();
		
		if(usersRepository.findAllByEmail(email).size() == 1) {
			throw new IllegalArgumentException("Email is already taken.");
		}
		else {
			Users.setName(name);
			Users.setEmail(email);
			Users.setPassword(password);
			Users.setIsPro(isPro);
		}
		
		return usersRepository.save(Users);
	}

	// RECIPE METHODS

	@Transactional
	public Recipe createRecipe(int userID, String recipeName, String recipeSteps, String rating) {
		Recipe recipe = new Recipe();
		Users user = usersRepository.findByUId(userID);
		if(user == null) {
			throw new IllegalArgumentException("User does not exist");
		}

		//Checks if user is pro first, otherwise return null
		if(user.isIsPro()) {
			recipe.setName(recipeName);
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
	public boolean authenticateUsers(String email, String password) {
		Users Users = usersRepository.findByEmail(email);
		
		if(Users == null) {
			throw new IllegalArgumentException("Account with given email does not exist.");
		}
		else {
			if(password.equals(Users.getPassword())) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}
