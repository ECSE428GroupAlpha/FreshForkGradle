package ca.mcgill.ecse428.freshfork.features;

import ca.mcgill.ecse428.freshfork.dao.RecipeRepository;
import ca.mcgill.ecse428.freshfork.dao.UsersRepository;
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

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemoveRecipeStepDefinitions {

    @Autowired
    FreshForkServices freshForkServices;

    @Autowired
    UsersRepository usersRepository;
    
    @Autowired
    RecipeRepository RecipeRepository;
    
    String rand = Integer.toString((int)(Math.random()*100000000));
    Users prouser;
    Users user;
    //no recipe pro user
    Users prouser1;
    Recipe recipe;
    Boolean tempRec;
    String error;
    //this is the scenerio number used to help with error detection
    int sceneNum;
    
    @Given("I am a pro user")
    public void i_am_a_pro_user() {
    	String rand1 = Integer.toString((int)(Math.random()*100000000));
        prouser = freshForkServices.createUser("RemoveRecipeTest"+rand1, "RemoveRecipeTest"+rand1+"@email.com", "password", true);
    }


    @Given("I have registered recipes on my account")
    public void i_have_registered_recipes_on_my_account() {
    	recipe = freshForkServices.createRecipe(prouser.getUId(), "Apple Sauce", "Stir the ingredients", "5");
    }
    @When("I request removal of a recipe {string} from my account")
    public void i_request_removal_of_a_recipe(String string) {
    	tempRec = freshForkServices.deleteRecipe(recipe.getRecipeID(), prouser);
    	
    }
    @Then("recipe {string} will be removed")
    public void recipe_will_be_removed(String string) {
    	assertTrue(tempRec);
    }
    
    @When("I request to delete a recipe that is not mine")
    public void i_request_to_delete_a_recipe_that_is_not_mine() {
    	prouser1 = freshForkServices.createUser("RemoveRecipeTest"+rand, "RemoveRecipeTest"+rand+"@email.com", "password", true);
    	recipe = freshForkServices.createRecipe(prouser.getUId(), "Apple Sauce"+rand, "Stir the ingredients", "5");
        try {
        	tempRec = freshForkServices.deleteRecipe(recipe.getRecipeID(),prouser1);
        }
        catch(Exception e) {
        	error = e.getMessage();
        }
    }
    
    @Then("a remove recipe error {string} message is issued")
    public void a_remove_recipe_error_message_is_issued(String string) {
    	assertEquals(error,string);
    }
    
    @Given("I am a non-pro user")
    public void i_am_a_non_pro_user() {
    	String rand2 = Integer.toString((int)(Math.random()*100000000));
    	user = freshForkServices.createUser("RemoveRecipeTest"+rand2, "RemoveRecipeTest"+rand2+"@email.com", "password", false);
    }


    @When("I request to delete a recipe")
    public void i_request_to_delete_a_recipe() {
    	recipe = RecipeRepository.findAll().iterator().next();
    	try {
        	tempRec = freshForkServices.deleteRecipe(recipe.getRecipeID(),user);
        }
        catch(Exception e) {
        	error = e.getMessage();
        }
    }
    
    @When("I request to delete a recipe that does not exist")
    public void i_request_to_delete_a_recipe_that_does_not_exist() {
    	prouser1 = freshForkServices.createUser("RemoveRecipeTest"+rand, "RemoveRecipeTest"+rand+"@email.com", "password", true);
    	try {
    		tempRec = freshForkServices.deleteRecipe(0,prouser1);
    	}
    	catch(Exception e) {
    		error = "Recipe not found";
    	}
    }

    

}
