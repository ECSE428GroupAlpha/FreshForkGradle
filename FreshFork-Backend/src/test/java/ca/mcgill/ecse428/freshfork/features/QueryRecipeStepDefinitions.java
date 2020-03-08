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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class QueryRecipeStepDefinitions {
    @Autowired
    FreshForkServices freshForkServices;

    @Autowired
    RecipeRepository recipeRepository;
    
    @Autowired
    DietRepository dietRepository;
    
    String error;
    Users user;
    String rand = Integer.toString((int)(Math.random()*100000000));
    Diet diet;
    Boolean done = true;
    List<Recipe> recipes=null;
    
    @Given("that I am not a user of the system")
    public void that_I_am_not_a_user_of_the_system() {
    	
    }
    
    @Given("there are recipes in the system")
    public void there_are_recipes_in_the_system() {
    	user = freshForkServices.createUser("queryrecipetest"+rand,"queryrecipetest"+rand+"@gmail.com" , "password", true);
        Recipe recipe = freshForkServices.createRecipe(user.getUId(),"Special meat sauce" + rand, "make the meat sauce", "5");
    }


    @When("I request to search for a recipe")
    public void i_request_to_search_for_a_recipe() {
    	if(user == null) {
    		try {
    	    	recipes = freshForkServices.searchRecipe("Special meat sauce" + rand, "this is nto real");
    		}
    		catch(Exception e) {
    			error =e.getMessage();
    		}
    	}
    	else {
    		recipes = freshForkServices.searchRecipe("Special meat sauce" + rand, user.getEmail());

    	}
    }
    	
    @Then("the system returns a list diet error message {string}")
    public void the_system_returns_a_list_diet_error_message(String string) {
        assertEquals(string,error);
    }
    
    @Given("that I am a user of the system")
    public void that_I_am_a_user_of_the_system() {
        user = freshForkServices.createUser("createDietTest"+rand, "createDietTest"+rand+"@email.com", "password", true);
    }

    @Then("the system returns a list of recipes within the search criteria")
    public void the_system_returns_a_list_of_recipes_within_the_search_criteria() {
    	assertNotNull(recipes);
    }
}
