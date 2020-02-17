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

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddUserStepDefinitions {
    String email;
    String username;
    String password;
    private boolean isPro;

    String dwightSchruteErrorMsg;

    @Autowired
    FreshForkServices freshForkServices;

    @Autowired
    UsersRepository usersRepository;

    @Given("^email (.*)2$")
    public void email_email(String email) {
        System.out.println(email);
        this.email = email;
    }

    @Given("^first name (.*)$")
    public void first_name_fname(String fname) {
    }
    @Given("^last name (.*)$")
    public void last_name_lname(String lname) {
    }
    @Given("^username (.*)$")
    public void username_uname(String username) {
        // Write code here that turns the phrase above into concrete actions
        this.username = username;
    }
    @Given("^password (.*)$")
    public void password_pword(String password) {
        this.password = password;
    }

    @Given("^account type (.*)$")
    public void account_type_acc_type(String accType) {
        this.isPro = accType.equals("Professional");
    }

    @When("^email (.*) requests to create a new account$")
    public void email_email_requests_to_create_a_new_account(String email) {
        freshForkServices.createUser(username, email, password, isPro);
    }

    @Then("a new user is created")
    public void a_new_user_is_created_with_user_id_user_id() {
        Users users = usersRepository.findByName(username);
        assertEquals(usersRepository.findByName(username).getName(), this.username);
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
