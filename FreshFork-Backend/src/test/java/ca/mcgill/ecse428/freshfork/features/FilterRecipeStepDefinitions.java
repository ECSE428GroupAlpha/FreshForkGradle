package ca.mcgill.ecse428.freshfork.features;

import ca.mcgill.ecse428.freshfork.dao.DietRepository;
import ca.mcgill.ecse428.freshfork.dao.RecipeRepository;
import ca.mcgill.ecse428.freshfork.model.Diet;
import ca.mcgill.ecse428.freshfork.model.Recipe;
import ca.mcgill.ecse428.freshfork.model.Users;
import ca.mcgill.ecse428.freshfork.services.FreshForkServices;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Arrays;

public class FilterRecipeStepDefinitions {
    @Autowired
    FreshForkServices freshForkServices;

    @Autowired
    RecipeRepository recipeRepository;
    
    @Autowired
    DietRepository dietRepository;

    Users user;
    String userEmail;
    String rand = Integer.toString((int)(Math.random()*100000000));
    List<Recipe> recipes;
    Recipe recipe;
    String error;
    Diet diet;
    
    
    @Given("I am a user of the freshfork system")
    public void i_am_a_user_of_the_freshfork_system() {
    	user = freshForkServices.createUser("filterrecipestest"+rand,"filterrecipetest"+rand+"@gmail.com" , "password", true);
    	userEmail = user.getEmail();
 
    }
    @Given("I am not a user of the freshfork system")
    public void i_am_not_a_user_of_the_freshfork_system() {
        userEmail = "thisisnotreal";
    }

    @Given("there are diets in the freshfork system")
    public void there_are_diets_in_the_freshfork_system() {
    	diet = freshForkServices.createDiet("dietfilter"+rand, user.getEmail());
    }

    @Given("there are recipes in the fresFork system")
    public void there_are_recipes_in_the_fresFork_system() {
        recipe = freshForkServices.createRecipe(user.getUId(), "this is a filter recipe recipe", "steps here", "3");
        freshForkServices.addRecipeToDiet("dietfilter"+rand, recipe.getRecipeID());
    }

    @Then("The system return a list of filtered recipes")
    public void the_system_return_a_list_of_filtered_recipes() {
        assertNotNull(recipes);
    }

    @Given("there are no diets")
    public void there_are_no_diets() {
        
    }

    @When("I choose to filter recipes by diet")
    public void i_choose_to_filter_recipes_by_diet() {
        try {
        	recipes = freshForkServices.filterRecipeByDiet("dietfilter"+rand,userEmail);
        }
        catch(Exception e) {
        	error = e.getMessage();
        }
    }

    @Then("The system returns a filter recipe error message {string}")
    public void the_system_returns_a_filter_recipe_error_message(String string) {
        assertEquals(string,error);
    }
}
