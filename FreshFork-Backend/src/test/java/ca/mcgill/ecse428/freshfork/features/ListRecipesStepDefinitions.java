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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.util.Arrays;

public class ListRecipesStepDefinitions {
    @Autowired
    FreshForkServices freshForkServices;

    @Autowired
    RecipeRepository recipeRepository;

    Users currentUser;
    String userEmail;

    Recipe recipe;
    String error;
    int outline = 2;

    @Given("I am a User logged into the FreshFork system")
    public void i_am_a_User_logged_into_the_FreshFork_system() {
        try {
            String rand = Integer.toString((int) (Math.random() * 1000000));
            userEmail = "listRecipesTest" + rand + "@email.com";
            currentUser = freshForkServices.createUser("listRecipesTest" + rand, userEmail, "password", true);
        } catch (Exception e) {
            throw e;
        }
    }

    @Given("I have registered diets on my account")
    public void i_have_registered_diets_on_my_account() {
        // Write code here that turns the phrase above into concrete actions
        try {    
            String rand = Integer.toString((int) (Math.random() * 1000000));
            freshForkServices.createDiet("Vegetarian"+rand, userEmail);
            freshForkServices.createDiet("Keto"+rand, userEmail);
            freshForkServices.createDiet("Vegan"+rand, userEmail);

            Set<Diet> testDiets = new HashSet<>();
            testDiets.add(freshForkServices.getDiet("Vegetarian"+rand));
            testDiets.add(freshForkServices.getDiet("Keto"+rand));
            testDiets.add(freshForkServices.getDiet("Vegan"+rand));

            currentUser.setDiet(testDiets);
        } catch (Exception e) {
            throw e;
        }
    }

    @When("I request to list all recipes")
    public void i_request_to_list_all_recipes() {
        // Write code here that turns the phrase above into concrete actions
        

        throw new io.cucumber.java.PendingException();
    }

    @Then("the system will show all recipes from my diet.")
    public void the_system_will_show_all_recipes_from_my_diet() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("I am an unregistered user of FreshFork system")
    public void i_am_an_unregistered_user_of_FreshFork_system() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the system will randomly select recipes")
    public void the_system_will_randomly_select_recipes() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("list all random recipes.")
    public void list_all_random_recipes() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}