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

public class LoginStepDefinitions {
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
    
    @Given("I am a user of the FreshFork system")
    public void i_am_a_user_of_the_FreshFork_system() {
        user = freshForkServices.createUser("LoginTest"+rand, "LoginTest"+rand+"@gmail.com", "password", false);
    }

    @When("I request to login to the FreshFork System with a valid email and password")
    public void i_request_to_login_to_the_FreshFork_System_with_a_valid_email_and_password() {
        try {
        	done = freshForkServices.authenticateUsers(user.getEmail(), user.getPassword());
        }catch( Exception e) {
        	error = e.getMessage();
        }
    }
    @Then("the system should login me in")
    public void the_system_should_login_me_in() {
        assertTrue(done);
    }
    
    @When("I request to login to the FreshFork System with an invalid email and correct password")
    public void i_request_to_login_to_the_FreshFork_System_with_an_invalid_email_and_correct_password() {
    	try {
        	done = freshForkServices.authenticateUsers("sadfhgldifu", user.getPassword());
        }catch( Exception e) {
        	error = e.getMessage();
        }
    }
    
    @When("I request to login to the FreshFork System with an valid email and incorrect password")
    public void i_request_to_login_to_the_FreshFork_System_with_an_valid_email_and_incorrect_password() {
    	try {
        	done = freshForkServices.authenticateUsers(user.getEmail(), "sdfyilgd");
        }catch( Exception e) {
        	error = e.getMessage();
        }
    }
    
    @Then("the system should return a login error message {string}")
    public void the_system_should_return_a_login_error_message(String string) {
    	assertEquals(string,error);
    }
    
    
    

}
