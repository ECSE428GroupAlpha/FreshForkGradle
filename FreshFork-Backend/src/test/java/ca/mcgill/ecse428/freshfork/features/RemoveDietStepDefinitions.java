package ca.mcgill.ecse428.freshfork.features;

import ca.mcgill.ecse428.freshfork.dao.DietRepository;
import ca.mcgill.ecse428.freshfork.dao.RecipeRepository;
import ca.mcgill.ecse428.freshfork.dao.UsersRepository;
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

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemoveDietStepDefinitions {

    @Autowired
    FreshForkServices freshForkServices;

    @Autowired
    UsersRepository usersRepository;
    
    @Autowired
    RecipeRepository RecipeRepository;
    
    @Autowired
    DietRepository dietRepository;
    
    String error;
    Users user;
    Users user2;
    String rand = Integer.toString((int)(Math.random()*100000000));
    String rand2 = Integer.toString((int)(Math.random()*100000000));
    Diet diet;
    Boolean done = false;
    boolean done2 = false;
    
    @Given("a pro user is logged on to the freshFork system")
    public void a_pro_user_is_logged_on_to_the_freshFork_system() {
        user = freshForkServices.createUser("removeDietTest"+rand, "createDietTest"+rand+"@email.com", "password", true);
        user2 = freshForkServices.createUser("removeDietTest"+rand2, "createDietTest"+rand2+"@email.com", "password", true);

    }

    @When("the user requests to remove a diet")
    public void the_user_requests_to_remove_a_diet() {
    	freshForkServices.createDiet("diet"+rand, user.getEmail());
    	try{
    		done = freshForkServices.removeDiet("diet"+rand, user2.getEmail());
    	}
    	catch(Exception e) {
    		error = e.getMessage();
    	}
    }
    @Then("the system will remove the diet from the system")
    public void the_system_will_remove_the_diet_from_the_system() {
        assertTrue(done);
    }
    
    @Given("a non-pro user is logged onto the freshFork system")
    public void a_non_pro_user_is_logged_onto_the_freshFork_system() {
        user = freshForkServices.createUser("RemoveDietTest"+rand, "createDietTest"+rand+"@email.com", "password", true);
        user2 = freshForkServices.createUser("RemoveDietTest"+rand2, "createDietTest"+rand2+"@email.com", "password", false);

    }


    @Then("the system will return an remove diet error message {string}")
    public void the_system_will_return_an_remove_diet_error_message(String string) {
        assertEquals(string,error);
    }
    
    @When("the user requests to remove a diet from the system that does not exist")
    public void the_user_requests_to_remove_a_diet_from_the_system_that_does_not_exist() {
    	try{
    		done = freshForkServices.removeDiet("diet", user2.getEmail());
    	}
    	catch(Exception e) {
    		error = e.getMessage();
    	}
    }
}
