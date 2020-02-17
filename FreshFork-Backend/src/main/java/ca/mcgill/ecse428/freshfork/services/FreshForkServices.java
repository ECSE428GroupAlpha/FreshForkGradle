package ca.mcgill.ecse428.freshfork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse428.freshfork.dao.*;
import ca.mcgill.ecse428.freshfork.model.*;

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


	// USER METHODS

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
	
	@Transactional
	public Iterable<Users> getAllUsers() {
		Iterable<Users> allUsers = usersRepository.findAll();
		
		return allUsers;
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
	public void addRecipeToDiet (String dietName, int recipeID) {
		Recipe recipeToAddTo = recipeRepository.findByRecipeID(recipeID);
		Diet dietToAdd = dietRepository.findByName(dietName);
		
		if(dietToAdd == null) {
			throw new IllegalArgumentException("Diet does not exist");
		}
		else if(recipeToAddTo == null) {
			throw new IllegalArgumentException("Recipe with give ID does not exist");
		}
		else {
			Set<Diet> diets = recipeToAddTo.getDiet();
			if(diets == null) {
				diets = new HashSet<Diet>();
			}
			diets.add(dietToAdd);
			recipeToAddTo.setDiet(diets);
			
			Set<Recipe> recipes = dietToAdd.getRecipe();
			if(recipes == null) {
				recipes = new HashSet<Recipe>();
			}
			recipes.add(recipeToAddTo);
			dietToAdd.setRecipe(recipes);
			
			recipeRepository.save(recipeToAddTo);
			dietRepository.save(dietToAdd);
		}
	}
	
	//DIET METHODS
	@Transactional
	public Diet createDiet(String dietName) {
		Diet diet = new Diet();
		
		if(dietRepository.findAllByName(dietName).size() == 1) {
			throw new IllegalArgumentException("Diet with given name has already been created.");
		}
		else {
			diet.setName(dietName);
		}
		
		return dietRepository.save(diet);
	}

	@Transactional
	public Recipe deleteRecipe(int recipeID) {
		
		Recipe recipeToDelete = recipeRepository.findByRecipeID(recipeID);
		if(recipeToDelete == null) {
			//Recipe could not be found, returning null
			return null;
		}
		else {
			//Store recipeName in new string before deleting recipe
			recipeRepository.delete(recipeToDelete);
			return recipeToDelete;
		}
		
	}

	@Transactional
	public List<Recipe> filterRecipeByDiet(String dietName) {
		List<Recipe> ret = new ArrayList<Recipe>();
		Diet dietToFilterBy = dietRepository.findByName(dietName);
		Iterable<Recipe> allRecipes = recipeRepository.findAll();
		
		if(dietToFilterBy == null) {
			throw new IllegalArgumentException("Diet does not exist.");
		}
		
		Iterator<Recipe> iter = allRecipes.iterator();
		
		while(iter.hasNext()) {
			Recipe temp = iter.next();
			Set<Diet> dietsOfTemp = temp.getDiet();
			for(Diet d : dietsOfTemp) {
				if(d.getName().equals(dietName)) {
					ret.add(temp);
				}
			}
		}
		
		return ret;
	}
	
	@Transactional
	public Iterable<Recipe> getAllRecipes() {
		Iterable<Recipe> allRecipes = recipeRepository.findAll();
		
		return allRecipes;
	}

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

	@Transactional
	public List<Diet> getAllDiets() {
		return dietRepository.findAll();
	}
	
	@Transactional
	public Diet getDiet(String dietName) {
		return dietRepository.findByName(dietName);
	}
	
	@Transactional
	public Recipe getRecipe(int recipeID) {
		return recipeRepository.findByRecipeID(recipeID);
	}
}
