package com.example.RevaIssue.E2E.steps.registration;

import com.example.RevaIssue.E2E.poms.RegisterPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegistrationStep {
    private final RegisterPage registerPage;

    public RegistrationStep(RegisterPage registerPage) {
        this.registerPage = registerPage;
    }

    @When("The {string} enters their information")
    public void the_user_enters_their_information(String role) {
        registerPage.registerUserInfo(role, "password");
    }
    @When("Selects the {string} role")
    public void selects_the_role(String role) {
        registerPage.registerRoleInfo(role);
    }
    @When("Clicks on the submit button")
    public void clicks_on_the_submit_button() {
        registerPage.submitRegistration();
    }
    @Then("The user should be registered and sent to login")
    public void the_user_should_be_registered_and_sent_to_login() {
        registerPage.verifyUserRegistration();
    }
}
