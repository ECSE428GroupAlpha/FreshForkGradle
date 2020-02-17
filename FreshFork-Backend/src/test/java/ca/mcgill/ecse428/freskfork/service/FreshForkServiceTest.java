package ca.mcgill.ecse428.freskfork.service;

import static org.junit.Assert.assertEquals;
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

import ca.mcgill.ecse428.freskfork.dao.*;
import ca.mcgill.ecse428.freskfork.model.*;
import ca.mcgill.ecse428.freskfork.services.*;
import ca.mcgill.ecse428.freskfork.dto.*;

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
		Users newUser;
		newUser = testService.createUser("Anthony", "abc@gmail.com", "antant123", true);
		
		Recipe testRecipe = new Recipe();
		testRecipe.setAuthor(newUser);
		testRecipe.setName("recipe1");
		testRecipe.setRating("5");
		testRecipe.setRecipeSteps("nice");
		
		Recipe newRecipe = testService.createRecipe(newUser.getUId(), "recipe1", "nice", "5");
		
		//assertEquals(testRecipe.getAuthor(), newRecipe.getAuthor());
		assertEquals(testRecipe.getName(), newRecipe.getName());
		assertEquals(testRecipe.getRating(), newRecipe.getRating());
		assertEquals(testRecipe.getRecipeSteps(), newRecipe.getRecipeSteps());
	}
	
	@Test
	public void testCreateUser() {
		String generator = null;
		generator = Integer.toString((int)(Math.random()*1000000000)) ;
		String name = "Tom"+generator ;
		String email = "tom"+generator+"@gmail.com";
		
		testService.createUser(name, email, "password", true);
		
		Iterable<Users> Users = usersRepository.findAll();
		Iterator<Users> allUsers = Users.iterator();
		boolean exist = false;
		while(allUsers.hasNext()) {
			Users testuser = allUsers.next();
			if(testuser.getName().matches(name)) {
				if(testuser.getEmail().matches(email)) {
					exist = true;
					break;
				}
					
			}
		}
		
		assertTrue(exist);
				
				
	}
	

}
