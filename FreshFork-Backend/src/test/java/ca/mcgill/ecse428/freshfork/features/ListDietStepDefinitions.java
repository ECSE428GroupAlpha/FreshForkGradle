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

import java.util.List;

public class ListDietStepDefinitions {

    @Autowired
    FreshForkServices freshForkServices;

    @Autowired
    UsersRepository usersRepository;
    
    @Autowired
    RecipeRepository RecipeRepository;
    
    @Autowired
    DietRepository dietRepository;
    
    String error;
    Users user=null;
    Users user2;
    String rand = Integer.toString((int)(Math.random()*100000000));
    String rand2 = Integer.toString((int)(Math.random()*100000000));
    Diet diet;
    Boolean done = false;
    boolean done2 = false;
    List<Diet> diets = null;
    
    @Given("I am user of the system")
    public void i_am_user_of_the_system() {
        user = freshForkServices.createUser("listDietTest"+rand, "listDietTest"+rand+"@email.com", "password", true);
        freshForkServices.createDiet("listdiet"+rand, user.getEmail());
    }
    
    @Given("I am not a user of the system")
    public void i_am_not_a_user_of_the_system() {
        
    }

    @When("I request to get the list of diets")
    public void i_request_to_get_the_list_of_diets() {
    	if(user == null) {
    		try {
    	        freshForkServices.getAllDiets("this does not exist");
    		}
    		catch(Exception e) {
    			error = e.getMessage();
    		}
    	}
    	if(user != null)
    		diets = freshForkServices.getAllDiets(user.getEmail());
    }
    @Then("the system will return a list of diets")
    public void the_system_will_return_a_list_of_diets() {
        assertTrue(diets != null);
    }
    
    @Then("The system will return a list diet error {string}")
    public void the_system_will_return_a_list_diet_error(String string) {
        assertEquals(string,error);
    }
}
