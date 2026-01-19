package com.example.RevaIssue.E2E.steps.registration;

import com.example.RevaIssue.E2E.poms.RegisterPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegistrationCancel {
    private final RegisterPage registerPage;

    public RegistrationCancel(RegisterPage registerPage) {
        this.registerPage = registerPage;
    }

    @When("The user clicks on the cancel button")
    public void the_user_clicks_on_the_cancel_button() {
        registerPage.cancelRegistration();
    }
    @Then("The user returns to the login page")
    public void the_user_returns_to_the_login_page() {
        registerPage.verifyOnLogin();
    }
}
