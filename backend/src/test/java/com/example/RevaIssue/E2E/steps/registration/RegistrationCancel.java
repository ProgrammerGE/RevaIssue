package com.example.RevaIssue.E2E.steps.registration;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.driver;
import static com.example.RevaIssue.E2E.fixtures.FixtureResources.registerPage;

public class RegistrationCancel {
    @When("The user clicks on the cancel button")
    public void the_user_clicks_on_the_cancel_button() {
        registerPage.cancelRegistration();
    }
    @Then("The user returns to the login page")
    public void the_user_returns_to_the_login_page() {
        Assertions.assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
    }
}
