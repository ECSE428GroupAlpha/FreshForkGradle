package ca.mcgill.ecse428.freshfork.controller;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse428.freshfork.dao.*;
import ca.mcgill.ecse428.freshfork.dto.*;
import ca.mcgill.ecse428.freshfork.model.*;
import ca.mcgill.ecse428.freshfork.services.FreshForkServices;

@RestController
public class UserRestController {
	
	@Autowired
	FreshForkServices freshfork;
	
	@Autowired
	UsersRepository usersRepository;
	
	//http://localhost:6212/users/create?name=ed&password=123456&email=adshasdh@aas&isPro=true
	@PostMapping("/users/create")
	public UserDto createUserController(@RequestParam(name = "name") String username, @RequestParam(name = "password") String password, @RequestParam(name = "email") String email, @RequestParam(name = "isPro") boolean isPro) {
		Users tempUser = freshfork.createUser(username, email, password, isPro);
		//Returns Dto is user was succesfully created, null otherwise
		if(tempUser != null) {
			UserDto returnUser = new UserDto(tempUser.getUId(), username, email, isPro);
			return returnUser;
		}
		else {
			return null;
		}
	}
	
	//http://localhost:6212/users/createRecipe?authorID=1&recipename=chicken&steps=adshasdh&rating=1
	@PostMapping("/users/createRecipe")
	public RecipeDto createRecipeController(@RequestParam(name = "authorID") int creatorID, @RequestParam(name = "recipename") String recipename, @RequestParam(name = "steps") String recipeSteps, @RequestParam(name = "rating") String rating) {
		//Try to create recipe, if we get an exception pass it up to front end
		try {
			Recipe tempRecipe = freshfork.createRecipe(creatorID, recipename, recipeSteps, rating);
			Users user = usersRepository.findByUId(creatorID);
			RecipeDto returnRecipe = new RecipeDto(recipename, user.getName(), recipeSteps, rating, tempRecipe.getRecipeID());
			return returnRecipe;
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	@PostMapping("/users/deleteRecipe")
	public int deleteRecipeController(@RequestParam(name = "recipeID") int recipeID) {
		//deleteRecipe returns a string, if string is null or empty we did not delete the recipe
		Recipe tempRecipe = freshfork.deleteRecipe(recipeID);
		if(tempRecipe == null) {
			return 1;
		}
		else {
			return 0;

		}
	}
	
	//http://localhost:6212/diet/create?dietname=keto
	@PostMapping("/diet/create")
	public DietDto createDietController(@RequestParam(name = "dietname") String dietName) {
		try {
			Diet tempDiet = freshfork.createDiet(dietName);
			DietDto returnDiet = new DietDto(tempDiet.getName());
			return returnDiet;
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	//http://localhost:6212/diet/all
	@GetMapping("/diet/all")
	public List<DietDto> getAllDiets(){
		try {
			return freshfork.getAllDiets()
					.stream()
					.map(diet -> new DietDto(diet.getName()))
					.collect(Collectors.toList());
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	//http://localhost:6212/recipe/addToDiet?dietname=keto&recipeid=2
	@PostMapping("/recipe/addToDiet")
	public void addRecipeToDietController(@RequestParam(name = "dietname") String dietName, @RequestParam(name = "recipeid") int recipeID) {
		freshfork.addRecipeToDiet(dietName, recipeID);
	}
	
	//http://localhost:6212/recipe/filterByDiet?dietname=keto
	@GetMapping("/recipe/filterByDiet")
	public List<RecipeDto> filterRecipesByDietController(@RequestParam(name = "dietname") String dietName) {
		List<Recipe> recipes = freshfork.filterRecipeByDiet(dietName);
		List<RecipeDto> recipeDtos = new ArrayList<RecipeDto>();
		
		for(Recipe r : recipes) {
			RecipeDto temp = new RecipeDto(r.getName(), r.getAuthor().getName(), r.getRecipeSteps(), r.getRating(), r.getRecipeID());
			recipeDtos.add(temp);
		}
		
		return recipeDtos;
	}
	
	//http://localhost:6212/recipe/list
	@GetMapping("/recipe/list")
	public List<RecipeDto> listAllRecipesController() {
		Iterable<Recipe> recipes = freshfork.getAllRecipes();
		List<RecipeDto> recipeDtos = new ArrayList<RecipeDto>();
		
		Iterator<Recipe> iter = recipes.iterator();
		
		while(iter.hasNext()) {
			Recipe r = iter.next();
			RecipeDto temp = new RecipeDto(r.getName(), r.getAuthor().getName(), r.getRecipeSteps(), r.getRating(), r.getRecipeID());
			recipeDtos.add(temp);
		}
		
		return recipeDtos;
	}
	
	@GetMapping("/login")
	public void loginController(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
		freshfork.authenticateUsers(email, password);
	}
}
