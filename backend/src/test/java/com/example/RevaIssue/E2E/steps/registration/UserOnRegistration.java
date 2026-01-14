package com.example.RevaIssue.E2E.steps.registration;

import io.cucumber.java.en.Given;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.registerPage;

public class UserOnRegistration {
    @Given("The user is on the registration page")
    public void the_user_is_on_the_registration_page() {
        registerPage.openRegistrationPage();
    }
}
