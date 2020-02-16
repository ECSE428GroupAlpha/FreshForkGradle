package ca.mcgill.ecse428.freskfork.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse428.freskfork.dto.*;
import ca.mcgill.ecse428.freskfork.model.*;
import ca.mcgill.ecse428.freskfork.services.FreshForkServices;

@RestController
public class UserRestController {
	
	@Autowired
	FreshForkServices freshfork;
	
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
	
	@PostMapping("/users/createRecipe")
	public RecipeDto createRecipeController(@RequestParam(name = "authorID") int creator, @RequestParam(name = "author") String author, @RequestParam(name = "steps") String recipeSteps, @RequestParam(name = "rating") String rating) {
		//Try to create recipe, if we get an exception pass it up to front end
		try {
			Recipe tempRecipe = freshfork.createRecipe(creator, recipeSteps, rating);
			RecipeDto returnRecipe = new RecipeDto(author, recipeSteps, rating, tempRecipe.getRecipeID());
			return returnRecipe;
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	@PostMapping("/users/deleteRecipe")
	public int deleteRecipeController(@RequestParam(name = "recipeID") int recipeID) {
		//deleteRecipe returns a string, if string is null or empty we did not delete the recipe
		String tempString = freshfork.deleteRecipe(recipeID);
		if(tempString == null || tempString.length() == 0) {
			return 1;
		}
		else {
			return 0;

		}
	}
}
