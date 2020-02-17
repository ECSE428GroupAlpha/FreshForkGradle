package ca.mcgill.ecse428.freshfork.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse428.freshfork.dao.*;
import ca.mcgill.ecse428.freshfork.model.*;
import ca.mcgill.ecse428.freshfork.services.*;
import ca.mcgill.ecse428.freshfork.dto.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class FreshForkServiceTest {
	@Autowired
	FreshForkServices testService;

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

	@Test
	public void testCreateRecipe() {
		Users newUser = testService.createUser("Anthony", "abc@gmail.com", "antant123", true);

		Recipe testRecipe = new Recipe();
		testRecipe.setAuthor(newUser);
		testRecipe.setName("recipe1");
		testRecipe.setRating("5");
		testRecipe.setRecipeSteps("cooking... and done!");

		// Recipe newRecipe = testService.createRecipe(newUser.getUId(), "recipe1", "nice", "5");
		Iterable<Recipe> Recipe = recipeRepository.findAll();
		Iterator<Recipe> allRecipes = Recipe.iterator();
		boolean exist = false;

		while (allRecipes.hasNext()) {
			Recipe r = allRecipes.next();
			if (r.getRecipeID() == testRecipe.getRecipeID()) {	
				// Fatal error : duplicate id
				exist = true;
				assertFalse(exist);
			}
			if (r.getName().matches(testRecipe.getName())) {
				if (r.getAuthor().getName().matches(testRecipe.getAuthor().getName())) {
					exist = true;
					break;
				}
			}
		}

		assertTrue(exist);

		// assertEquals(testRecipe.getAuthor(), newRecipe.getAuthor());
		// assertEquals(testRecipe.getName(), newRecipe.getName());
		// assertEquals(testRecipe.getRating(), newRecipe.getRating());
		// assertEquals(testRecipe.getRecipeSteps(), newRecipe.getRecipeSteps());
	}

	@Test
	public void testCreateUser() {
		String generator = null;
		generator = Integer.toString((int) (Math.random() * 1000000000));
		String name = "Tom" + generator;
		String email = "tom" + generator + "@gmail.com";
		String password = "password";

		testService.createUser(name, email, password, true);

		Iterable<Users> Users = usersRepository.findAll();
		Iterator<Users> allUsers = Users.iterator();
		boolean exist = false;
		while (allUsers.hasNext()) {
			Users testuser = allUsers.next();
			if (testuser.getName().matches(name)) {
				if (testuser.getEmail().matches(email)) {
					if (testuser.getPassword().matches(password)) {
						exist = true;
						break;
					}
				}
			}
		}

		assertTrue(exist);

	}

	@Test
	public void testLogin() {
		Users testUser = createRandomTom();

		boolean authenticated = testService.authenticateUsers(testUser.getEmail(), testUser.getPassword());
		boolean notauthenticated1 = testService.authenticateUsers(testUser.getEmail(), "somethignrandom");
		String error = null;
		try {
			testService.authenticateUsers("somethignrandom", testUser.getPassword());
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertTrue(authenticated);
		assertFalse(notauthenticated1);
		assertEquals("Account with given email does not exist.", error);

	}

	// creates a user with unique name and email
	public Users createRandomTom() {
		String generator = null;
		generator = Integer.toString((int) (Math.random() * 1000000000));
		String name = "Tom" + generator;
		String email = "tom" + generator + "@gmail.com";
		Users user = testService.createUser(name, email, "password", true);
		return user;
	}

}
