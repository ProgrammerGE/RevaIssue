package com.example.RevaIssue.E2E.steps.popups.open_close;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.projectPage;

public class PopupUpdateIssue {
    @Given("The user is on the project page")
    public void the_user_is_on_the_project_page() {
        projectPage.openProjectPage();
    }
    @When("Users click the update issue button")
    public void users_click_the_update_issue_button() {
        projectPage.clickUpdateIssue();
    }
    @Then("A popup will appear for updating the issue details")
    public void a_popup_will_appear_for_updating_an_issue_for_the_project() {
        Assertions.assertTrue(projectPage.isUpdateIssueOpen());
    }
}
