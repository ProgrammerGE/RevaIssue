package com.example.RevaIssue.E2E.steps.popups.submitting;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.hubpage;

public class SubmitUpdateProject {
    @When("The admin clicks on the update project button")
    public void the_admin_clicks_on_the_update_project_button(){
        hubpage.clickUpdateProject();
    }
    @When("inputs the updated project details")
    public void inputs_the_updated_project_details(){
        hubpage.enterInfo("New Project", "New Description");
    }
    @When("clicks the submit update project button")
    public void clicks_the_submit_update_project_button(){
        hubpage.submitUpdatedProject();
    }
    @Then("A project will be updated")
    public void a_project_will_be_updated() {
        Assertions.assertTrue(hubpage.isUpdatePopupOpen());
    }
}
