package ca.mcgill.ecse428.freshfork.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
	private static final String user1 = "Anthony";
	private static final String user1Password = "abcabc123";
	private static final String user1Email = "antant123@gmail.com";
	
	private static final String user2 = "Julien";
	private static final String user2Password = "absabs124";
	private static final String user2Email = "juju123@gmail.com";
	
	private static final String user3 = "Camille";
	private static final String user3Password = "camcam123";
	private static final String user3Email = "camcam123@gmail.com";
	
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
		Users newUser = testService.createUser(user1, user1Email, user1Password, true);
		Users nonProUser = testService.createUser(user2, user2Email, user2Password, false);
		IllegalArgumentException e1 = new IllegalArgumentException("User is not a professional");
		IllegalArgumentException e2 = new IllegalArgumentException("User does not exist");
		Recipe testRecipe = new Recipe();
		testRecipe.setAuthor(newUser);
		testRecipe.setName("recipe1");
		testRecipe.setRating("5");
		testRecipe.setRecipeSteps("nice");
		
		Recipe newRecipe = testService.createRecipe(newUser.getUId(), "recipe1", "nice", "5");
		
		//Test the created recipe
		assertEquals(testRecipe.getAuthor().getUId(), newRecipe.getAuthor().getUId());
		assertEquals(testRecipe.getName(), newRecipe.getName());
		assertEquals(testRecipe.getRating(), newRecipe.getRating());
		assertEquals(testRecipe.getRecipeSteps(), newRecipe.getRecipeSteps());
		
		//Try to create a recipe with non pro users
		try {
			testService.createRecipe(nonProUser.getUId(), "recipe2", "test", "10");

		}
		catch(IllegalArgumentException e) {
			assertEquals(e.getMessage(), e1.getMessage());
		}
		
		//Try to create with fake user
		try {
			testService.createRecipe(99999, "recipe2", "test", "10");
		}
		catch(IllegalArgumentException e) {
			assertEquals(e.getMessage(), e2.getMessage());
		}
	}
	
	@Test
	public void testDeleteRecipe() {
		Users newUser = testService.createUser(user3, user3Email, user3Password, true);
		Recipe testRecipe = testService.createRecipe(newUser.getUId(), "recipe1", "yummy", "5");
		int recipeID = testRecipe.getRecipeID();
		
		Recipe deletedRecipe = testService.deleteRecipe(testRecipe.getRecipeID());
		
		assertEquals(recipeID, deletedRecipe.getRecipeID());
		assertNull(testService.deleteRecipe(0));

		testRecipe.setRecipeSteps("cooking... and done!");

		
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
				}
			}
		}

		assertFalse(exist);
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
	
	@Test
	public void testAddRecipeToDiet() {
		Diet tempDiet = null;
		Recipe tempRecipe = null;
		Users newProUser = testService.createUser(user3+"h", user3Email+"h", user3Password, true);
		Recipe newRecipe = testService.createRecipe(newProUser.getUId(), "recipe1", "nice", "5");
		Diet newDiet = testService.createDiet("Diet1");
	
		assertNull(newRecipe.getDiet());
		assertNull(newDiet.getRecipe());
		
		testService.addRecipeToDiet(newDiet.getName(), newRecipe.getRecipeID());
		
		newRecipe = testService.getRecipe(newRecipe.getRecipeID());
		newDiet = testService.getDiet(newDiet.getName());
		
		assertNotNull(newRecipe);
		assertNotNull(newDiet);
		
		Set<Diet> recipeDiets= newRecipe.getDiet();
		Iterator<Diet> dietIterator = recipeDiets.iterator();
		while(dietIterator.hasNext()) {
			tempDiet = dietIterator.next();
			if(tempDiet.getName().equals(newDiet.getName())) {
				break;
			}
		}
		if(tempDiet != null) {
			assertEquals(tempDiet.getName(), newDiet.getName());
		}
		else {
			assertTrue(false);
		}
		
		Set<Recipe> recipesInDiet = newDiet.getRecipe();
		Iterator<Recipe> recipeIterator = recipesInDiet.iterator();
		while(recipeIterator.hasNext()) {
			tempRecipe = recipeIterator.next();
			if(tempRecipe.getRecipeID() == newRecipe.getRecipeID()) {
				break;
			}
		}
		if(tempRecipe != null) {
			assertEquals(tempRecipe.getRecipeID(), newRecipe.getRecipeID());
		}
		else {
			assertTrue(false);
		}
	
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
