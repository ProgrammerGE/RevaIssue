package com.example.RevaIssue.E2E.steps.popups.submitting;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.hubpage;
import static com.example.RevaIssue.E2E.fixtures.FixtureResources.projectPage;

public class SubmitUpdateIssue {
    @When("Users click the update issue button")
    public void users_click_the_update_issue_button() {
        projectPage.clickUpdateIssue();
    }
    @When("inputs the updated issue details")
    public void inputs_the_updated_issue_details(){
        projectPage.filloutUpdatedIssueInformation("Updated Title", "Updated description", "2", "3");
    }
    @When("clicks the submit update issue button")
    public void clicks_the_submit_update_issue_button(){
        projectPage.submitUpdatedIssue();
    }
    @Then("the issue details will be updated")
    public void the_issue_details_will_be_updated() {
        Assertions.assertTrue(projectPage.isUpdateIssueOpen());
    }
}
