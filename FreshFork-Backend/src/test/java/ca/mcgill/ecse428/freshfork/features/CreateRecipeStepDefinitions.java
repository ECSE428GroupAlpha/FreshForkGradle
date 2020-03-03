package ca.mcgill.ecse428.freshfork.features;

import ca.mcgill.ecse428.freshfork.dao.RecipeRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateRecipeStepDefinitions {
    @Autowired
    FreshForkServices freshForkServices;

    @Autowired
    RecipeRepository recipeRepository;

    Users currentUser;

    Recipe recipe;
    String error;
    int outline = 2;

    @Given("I am an existing user of type Professional")
    public void i_am_an_existing_user_of_type_Professional() {
        try {
        	String rand = Integer.toString((int)(Math.random()*100000000));
            currentUser = freshForkServices.createUser("createRecipeTest"+rand, "createRecipeTest"+rand+"@email.com", "password", true);
        } catch (Exception e) {
            if(!e.getMessage().equals("Email is already taken")){
                throw e;
            }
        }
    }

    @When("^I provide a (.*), (.*), (.*)$")
    public void i_provide_a_Apple_Sauce_Stir_the_ingredients(String recipeName, String recipeSteps, String rating) {
    	try {
            recipe = freshForkServices.createRecipe(currentUser.getUId(), recipeName, recipeSteps, rating);
    	}
    	catch(Exception e) {
    		error = e.getMessage();
    		if (error.matches("Invalid input for rating"))
    			outline = 1;
    	}
    }
    @Then("a new recipe with a randomnly generated {int} should be created")
    public void a_new_recipe_with_a_randomnly_generated_should_be_created(Integer int1) {
        assertTrue(recipe.getRecipeID() >= 0);
    }

    @Then("the recipe should be saved to the database")
    public void the_recipe_should_be_saved_to_the_database() {
        Recipe recipe = recipeRepository.findByRecipeID(this.recipe.getRecipeID());
        assertEquals(recipe.getRecipeID(), this.recipe.getRecipeID());
    }
    
    @Then("an {string} message is issued")
    public void an_message_is_issued(String string) {
    	if(outline == 1)
    		assertEquals(error,"Invalid input for rating");
    	else
    		assertEquals(error,"User is not a professional");
    }

    @Given("I am an existing user not of type Professional")
    public void i_am_an_existing_user_not_of_type_Professional() {
    	String rand = Integer.toString((int)(Math.random()*100000000));
        currentUser = freshForkServices.createUser("createRecipeTest"+rand, "createRecipeTest"+rand+"@email.com", "password", false);
    }

    @When("I provide a Apple Sauce, Stir the ingredients and <ingredients>")
    public void i_provide_a_Apple_Sauce_Stir_the_ingredients_and_ingredients() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

//
//    @Then("a new recipe with a randomnly generated {int} should be created with a default {int} value of {int}")
//    public void a_new_recipe_with_a_randomnly_generated_should_be_created_with_a_default_value_of(Integer int1, Integer int2, Integer int3) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }
//
//    @Then("a new recipe with a randomnly generated {int} should be created with a default {int} value of {int}")
//    public void a_new_recipe_with_a_randomnly_generated_should_be_created_with_a_default_value_of(Integer int1, Integer int2, Integer int3) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }

}
