package com.example.RevaIssue.E2E.steps;

import com.example.RevaIssue.E2E.poms.HubPage;
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

@ScenarioScope
public class LoginSteps {
    private final UserRepository userRepository;
    private final UserService userService;
    private final LoginPage loginPage;
    private final HubPage hubPage;

    public LoginSteps(UserRepository userRepository, UserService userService, LoginPage loginPage, HubPage hubPage) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.loginPage = loginPage;
        this.hubPage = hubPage;
    }

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
        if(this.hubPage.isOnHubpage()) {
            System.out.println("user logged in");
        } else {
            throw new AssertionError("login failed");
        }
    }

    @Then("The login button is disabled because of invalid credentials")
    public void loginButtonDisabledBecauseInvalidCredentials () {
        if(!this.hubPage.isOnHubpage()) {
            System.out.println("login failed as expected");
        } else {
            throw new AssertionError("AUTHENTICATION PASSED WITH INVALID CREDENTIALS");
        }
    }
}
