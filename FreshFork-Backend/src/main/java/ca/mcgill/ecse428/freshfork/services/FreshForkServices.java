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
	public Boolean changePassword(String email, String existingpassword, String newpassword) {
		Users user = usersRepository.findByEmail(email);
		if(user == null) {
			throw new IllegalArgumentException("incorrect email");
		}
		if(user.getPassword().matches(existingpassword)) {
			user.setPassword(newpassword);
			return true;
		}
		else {
			throw new IllegalArgumentException("incorrect current password");
		}

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
		if(Integer.parseInt(rating) < 0 ) {
			throw new IllegalArgumentException("Invalid input for rating");
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
		
		return recipeToAddTo;
	}
	
	@Transactional
	public Boolean deleteRecipe(int recipeID,Users user) {
		Recipe recipeToDelete;
		if(!user.isIsPro()) {
			throw new IllegalArgumentException("User not pro");
		}
		try {
			recipeToDelete = recipeRepository.findByRecipeID(recipeID);
		}
		catch(Exception e) {
				throw new IllegalArgumentException("Recipe not found");
		}

		if(recipeToDelete.getAuthor().getUId() != user.getUId()) {
			throw new IllegalArgumentException("User is not creator");
		}
		
		else {
			//Store recipeName in new string before deleting recipe
			recipeRepository.delete(recipeToDelete);
			return true;
		}
		
	}
	
	//DIET METHODS
	@Transactional
	public Diet createDiet(String dietName, String email) {
		if(!usersRepository.findByEmail(email).isIsPro()) {
			throw new IllegalArgumentException("User is not professional");
		}
		Diet diet = new Diet();
		
		if(dietRepository.findAllByName(dietName).size() == 1) {
			throw new IllegalArgumentException("Diet with given name has already been created.");
		}
		else {
			diet.setName(dietName);
		}
		
		return dietRepository.save(diet);
	}
	
	public Boolean removeDiet(String dietName, String email) {
		Users user = null;
		Diet diet=null;
		try {
			user = usersRepository.findByEmail(email);
		}
		catch(Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
		if(user==null) {
			throw new IllegalArgumentException("Email not found");
		}
		if(!user.isIsPro()) {
			throw new IllegalArgumentException("User not Pro");
		}
		diet = dietRepository.findByName(dietName);
		
		if(diet==null) {
			throw new IllegalArgumentException("diet does not exist");
		}
		
		try{
			dietRepository.delete(diet);
		}
		catch(Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
		return true;
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
	public Boolean authenticateUsers(String email, String password) {
		Users Users = usersRepository.findByEmail(email);
		
		if(Users == null) {
			throw new IllegalArgumentException("Account with given email does not exist.");
		}
		else {
			if(password.equals(Users.getPassword())) {
				return true;
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
	
	@Transactional
	public Diet getDiet(String dietName) {
		return dietRepository.findByName(dietName);
	}
	
	@Transactional
	public Recipe getRecipe(int recipeID) {
		return recipeRepository.findByRecipeID(recipeID);
	}
	
	@Transactional
	public void deleteUser(int userID) {
		Users userToDelete = null;
		//Try and find the user first, if you cant find the user throw an exception
		userToDelete = usersRepository.findByUId(userID);
		if(userToDelete == null) {
			throw new IllegalArgumentException("User not found");
		}
		else {
			usersRepository.delete(userToDelete);
		}
	}
}
