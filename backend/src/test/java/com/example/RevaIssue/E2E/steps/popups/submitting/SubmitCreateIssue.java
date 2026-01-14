package com.example.RevaIssue.E2E.steps.popups.submitting;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.hubpage;
import static com.example.RevaIssue.E2E.fixtures.FixtureResources.projectPage;

public class SubmitCreateIssue {
    @When("The tester clicks on the create issue button")
    public void the_tester_clicks_on_the_create_issue_button() {
        projectPage.clickCreateIssue();
    }
    @When("inputs the issue details")
    public void inputs_the_issue_details(){
        projectPage.filloutIssueInformation("Issue Title", "A description", "1", "1");
    }
    @When("clicks the submit new issue button")
    public void clicks_the_submit_new_issue_button(){
        projectPage.submitNewIssue();
    }
    @Then("A issue will be created")
    public void a_issue_will_be_created() {
        Assertions.assertTrue(projectPage.isCreateIssueOpen());
    }
}
