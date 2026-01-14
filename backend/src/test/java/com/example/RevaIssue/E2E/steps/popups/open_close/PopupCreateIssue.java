package com.example.RevaIssue.E2E.steps.popups.open_close;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.projectPage;

public class PopupCreateIssue {
    @Given("The user is on the project page")
    public void the_user_is_on_the_project_page() {
        projectPage.openProjectPage();
    }
    @When("The tester clicks on the create issue button")
    public void the_tester_clicks_on_the_create_issue_button() {
        projectPage.clickCreateIssue();
    }
    @Then("A popup will appear for creating an issue for the project")
    public void a_popup_will_appear_for_creating_an_issue_for_the_project() {
        Assertions.assertTrue(projectPage.isCreateIssueOpen());
    }
}
