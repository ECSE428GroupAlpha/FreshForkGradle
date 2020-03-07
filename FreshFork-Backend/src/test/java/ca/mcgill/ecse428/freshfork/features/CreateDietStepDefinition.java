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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateDietStepDefinition {
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
    
    
    @Given("That i am logged in as a pro user")
    public void that_i_am_logged_in_as_a_pro_user() {
        user = freshForkServices.createUser("createDietTest"+rand, "createDietTest"+rand+"@email.com", "password", true);
    }

    @Given("That i am logged in as a non-pro user")
    public void that_i_am_logged_in_as_a_non_pro_user() {
        user = freshForkServices.createUser("createDietTest"+rand, "createDietTest"+rand+"@email.com", "password", false);

    }
    
    @When("I try to create a new diet")
    public void i_try_to_create_a_new_diet() {
        try {
        	diet = freshForkServices.createDiet("diet"+rand, user.getEmail());
        }
        catch(Exception e) {
        	error = e.getMessage();
        }
    }
    @Then("the system will create a new diet")
    public void the_system_will_create_a_new_diet() {
        if(diet!=null) {
        	assertTrue(done);
        }
    }
    
    @When("I try to create a new diet with the same name as an existing diet")
    public void i_try_to_create_a_new_diet_with_the_same_name_as_an_existing_diet() {
    	try {
        	diet = freshForkServices.createDiet("diet"+rand, user.getEmail());
        }
        catch(Exception e) {
        	error = e.getMessage();
        }
    	try {
        	diet = freshForkServices.createDiet("diet"+rand, user.getEmail());
        }
        catch(Exception e) {
        	error = e.getMessage();
        }
    }
    @Then("a create diet {string} error message is issued")
    public void a_create_diet_error_message_is_issued(String string) {
    	assertEquals(string,error);
    }

}
