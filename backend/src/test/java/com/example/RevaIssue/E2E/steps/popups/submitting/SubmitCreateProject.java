package com.example.RevaIssue.E2E.steps.popups.submitting;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.hubpage;

public class SubmitCreateProject {
    @Given("The admin is on the hub page")
    public void the_admin_is_on_the_hub_page() {
        hubpage.openHubPage();
    }
    @When("The admin clicks on the create project button")
    public void the_admin_clicks_on_the_create_project_button(){
        hubpage.clickCreateProject();
    }
    @When("inputs the project details")
    public void inputs_the_project_details(){
        hubpage.enterInfo("New Project", "New Description");
    }
    @When("clicks the submit button")
    public void clicks_the_submit_button(){
        hubpage.submitNewProject();
    }
    @Then("A project will be created")
    public void a_project_will_be_created() {
        Assertions.assertTrue(hubpage.isCreatePopupOpen());
    }
}
