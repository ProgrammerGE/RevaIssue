package com.example.RevaIssue.E2E.steps.registration;

import com.example.RevaIssue.E2E.poms.RegisterPage;
import io.cucumber.java.en.Given;

public class UserOnRegistration {
    private final RegisterPage registerPage;

    public UserOnRegistration(RegisterPage registerPage) {
        this.registerPage = registerPage;
    }

    @Given("The user is on the registration page")
    public void the_user_is_on_the_registration_page() {
        registerPage.openRegistrationPage();
    }
}
