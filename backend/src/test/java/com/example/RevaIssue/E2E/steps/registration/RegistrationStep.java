package com.example.RevaIssue.E2E.steps.registration;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.driver;
import static com.example.RevaIssue.E2E.fixtures.FixtureResources.registerPage;

public class RegistrationStep {
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
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.className("login-form")));
        Assertions.assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
    }
}
