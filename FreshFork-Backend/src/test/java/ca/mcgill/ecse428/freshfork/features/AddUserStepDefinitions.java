package ca.mcgill.ecse428.freshfork.features;

import ca.mcgill.ecse428.freshfork.dao.UsersRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddUserStepDefinitions {
    String email;
    String username;
    String password;
    private boolean isPro;
    String name;
    String password1;
    String email1;
    boolean isPro1;
    Users user;

    String dwightSchruteErrorMsg;

    @Autowired
    FreshForkServices freshForkServices;

    @Autowired
    UsersRepository usersRepository;

    @Given("the user submits the name,email, password and pro atrribute")
    public void the_user_submits_the_name_email_password_and_pro_atrribute() {
        name = "tom";
        password1 = "password";
        isPro1 = true;
        email1 = "tom@gmail.com";
    }

    @When("user request to create a new account on the system")
    public void user_request_to_create_a_new_account_on_the_system() {
        user = freshForkServices.createUser(name, email1, password1, isPro1);
    }
    @Then("the system will create a new account")
    public void the_system_will_create_a_new_account() {
        assertNotNull(user);
    }


    @Given("dwight@schrute.com is a registered email of an existing user of FreshFork")
    public void dwight_schrute_com_is_a_registered_email_of_an_existing_user_of_FreshFork() {
        freshForkServices.createUser("dwight_schrute", "dwight@schrute.com", password, isPro);
    }

    @When("dwight@schrute.com requests to create a new account")
    public void dwight_schrute_com_requests_to_create_a_new_account() {
        try {
            freshForkServices.createUser("dwight_schrute", "dwight@schrute.com", password, isPro);
        } catch (Exception e) {
            dwightSchruteErrorMsg = e.getMessage();
        }
    }
    @Then("a {string} message is issued")
    public void a_message_is_issued(String string) {
        assertEquals(this.dwightSchruteErrorMsg, "Email is already taken.");
    }
}
