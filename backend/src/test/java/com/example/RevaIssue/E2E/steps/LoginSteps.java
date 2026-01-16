package com.example.RevaIssue.E2E.steps;

import com.example.RevaIssue.E2E.poms.LoginPage;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.service.UserService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;

@ScenarioScope
public class LoginSteps {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginPage loginPage;

    @Given("A user exists with username {string} and password {string}")
    public void aUserExists(String username, String password) {
        if(userRepository.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setUserRole(UserRole.TESTER);
            userService.createUser(user);
        }
    }

    @And("The user is on the login page")
    public void theUserIsOnLoginPage() {
        loginPage.goToLogin();
    }

    @When("They enter a username {string}")
    public void theyEnterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @And("They enter a password {string}")
    public void theyEnterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @And("They click the login button")
    public void theyClickTheLoginButton() {
        this.loginPage.clickLogin();
    }

    @Then("They are directed to the hubpage")
    public void theyAreDirectedToTheHubpage () {
        if(this.loginPage.isAtDashboard()) {
            System.out.println("User Logged In");
        }
    }

    @Then("They remain on the login page")
    public void theyRemainOnLoginPage () {
        if(!this.loginPage.isAtDashboard()) {
            System.out.println("User Login Failed");
        }
    }
}
