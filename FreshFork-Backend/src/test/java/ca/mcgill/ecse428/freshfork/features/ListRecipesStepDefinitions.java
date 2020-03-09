package ca.mcgill.ecse428.freshfork.features;

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

public class ListRecipesStepDefinitions {
    @Autowired
    FreshForkServices freshForkServices;

    @Autowired
    RecipeRepository recipeRepository;

    Users user;
    String userEmail;
    String rand = Integer.toString((int)(Math.random()*100000000));
    Iterable<Recipe> recipes;
    Recipe recipe;
    String error;
    int outline = 2;

    @Given("I am a User logged into the FreshFork system")
    public void i_am_a_User_logged_into_the_FreshFork_system() {
    	user = freshForkServices.createUser("queryrecipetest"+rand,"queryrecipetest"+rand+"@gmail.com" , "password", true);
    	userEmail = user.getEmail();
    }

    @Given("There are recipes on the system")
    public void there_are_recipes_on_the_system() {
        recipe = freshForkServices.createRecipe(user.getUId(), "listrecipe"+rand, "these are the steps to this recipe", "4");
    }

    @Then("the system will show all recipes from my diet.")
    public void the_system_will_show_all_recipes_from_my_diet() {
        assertNotNull(recipes);
    }

    @Given("I am an unregistered user of FreshFork system")
    public void i_am_an_unregistered_user_of_FreshFork_system() {
        userEmail="thisdoesntexist";
    }

    @When("I request to list all recipes")
    public void i_request_to_list_all_recipes() {
        try {
        	recipes = freshForkServices.getAllRecipes(userEmail);
        }
        catch(Exception e) {
        	error = e.getMessage();
        }
    }

    @Then("the system will return a list recipe error {string}")
    public void the_system_will_return_a_list_recipe_error(String string) {
        assertEquals(string,error);
    }
}