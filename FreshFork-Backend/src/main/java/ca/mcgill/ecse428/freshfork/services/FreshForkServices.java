package ca.mcgill.ecse428.freshfork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse428.freshfork.dao.*;
import ca.mcgill.ecse428.freshfork.dto.RecipeDto;
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
	
	public Recipe addRecipeToDiet (String dietName, int recipeID) {
		Recipe recipeToAddTo = recipeRepository.findByRecipeID(recipeID);
		Diet dietToAdd = dietRepository.findByName(dietName);
		
		if(dietToAdd == null) {
			throw new IllegalArgumentException("Diet does not exist");
		}
		else if(recipeToAddTo == null) {
			throw new IllegalArgumentException("Recipe with give ID does not exist");
		}
		else {
			System.out.println(dietToAdd.getName());
			Iterable<Diet> ds = recipeToAddTo.getDiet();
			for(Diet d : ds) {
				System.out.println("old diets " + d.getName());
				if(d.getName().equals(dietToAdd.getName())) {
					throw new IllegalArgumentException("Recipe is already part of the diet");
				}
			}
			
			Set<Diet> diets = recipeToAddTo.getDiet();
			diets.add(dietToAdd);
			recipeToAddTo.setDiet(diets);
		}
		
		return recipeRepository.save(recipeToAddTo);
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
		
		for(Recipe temp : allRecipes) {
			Set<Diet> dietsOfTemp = temp.getDiet();
			for(Diet d : dietsOfTemp) {
				System.out.println(d.getName());
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
	
	@Transactional 
	public List<Recipe> searchRecipe(String searchString) {
		Iterable<Recipe> recipes = getAllRecipes();
		List<Recipe> rs = new ArrayList<Recipe>();
		
		Iterator<Recipe> iter = recipes.iterator();
		
		while(iter.hasNext()) {
			Recipe r = iter.next();
			if(r.getName().indexOf(searchString) != -1) {
				rs.add(r);
			}
		}
		
		return rs;
	}

	// AUTHENTICATION

	@Transactional
	public void authenticateUsers(String email, String password) {
		Users Users = usersRepository.findByEmail(email);
		
		if(Users == null) {
			throw new IllegalArgumentException("Account with given email does not exist.");
		}
		else {
			if(password.equals(Users.getPassword())) {
				return;
			}
			else {
				throw new IllegalArgumentException("Incorrect password.");
			}
		}
	}

	@Transactional
	public List<Diet> getAllDiets() {
		return dietRepository.findAll();
	}
}
