package com.example.RevaIssue.E2E.steps.popups.submitting;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.hubpage;

public class SubmitDeleteProject {
    @When("The admin clicks on the delete project button")
    public void the_admin_clicks_on_the_create_project_button(){
        hubpage.clickDeleteProject();
    }
    @When("clicks the confirm delete button")
    public void clicks_the_confirm_delete_button(){
        hubpage.clickDeleteConfirm();
    }
    @Then("A project will be delete")
    public void a_project_will_be_delete() {
        Assertions.assertTrue(hubpage.isDeletePopupOpen());
    }
}
