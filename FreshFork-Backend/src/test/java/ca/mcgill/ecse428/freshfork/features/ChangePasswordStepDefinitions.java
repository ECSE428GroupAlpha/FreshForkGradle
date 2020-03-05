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

public class ChangePasswordStepDefinitions {

    @Autowired
    FreshForkServices freshForkServices;

    @Autowired
    UsersRepository usersRepository;
    
    @Autowired
    RecipeRepository RecipeRepository;
    
    String rand = Integer.toString((int)(Math.random()*100000000));
    Users user;
    String error;
    boolean done = false;

    
    @Given("I am logged into FreshFork with a valid account")
    public void i_am_logged_into_FreshFork_with_a_valid_account() {
        user = freshForkServices.createUser("ChangePasswordTest"+rand, "ChangePasswordTest"+rand+"@gmail.com", "password", false);
    }
    
    @When("I request to change my account password with the correct current password")
    public void i_request_to_change_my_account_password_with_the_correct_current_password() {
    	try {
        	done = freshForkServices.changePassword(user.getEmail(), user.getPassword(), "newpassword"); 
        }
    	catch(Exception e) {
    		error = e.getMessage();
    	}
    }
    @Then("the system should change the password to the new password")
    public void the_system_should_change_the_password_to_the_new_password() {
        assertTrue(done);        
    }
    
    @When("I request to change my account password with the incorrect current password")
    public void i_request_to_change_my_account_password_with_the_incorrect_current_password() {
    	try {
        	done = freshForkServices.changePassword(user.getEmail(), "dfgsldfi", "newpassword"); 
        }
    	catch(Exception e) {
    		error = e.getMessage();
    	}
    	
    }
    
    @When("I request to change my account password with incorrect email")
    public void i_request_to_change_my_account_password_with_incorrect_email() {
    	try {
        	done = freshForkServices.changePassword("tsgufg", user.getPassword(), "newpassword"); 
        }
    	catch(Exception e) {
    		error = e.getMessage();
    	}
    }

    
    @Then("the system should return an error message {string}")
    public void the_system_should_return_an_error_message(String string) {
        assertEquals(string,error);
    }
}
