package com.example.RevaIssue.E2E.steps.project;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.projectPage;

public class ProjectSteps {
    @Given("I am an admin")
    public void i_am_an_admin() {
        projectPage.login("admin");
    }
    @When("I open up a project")
    public void i_open_up_a_project() {
        projectPage.openProjectPage("admin");
    }
    @When("I add  users to the project")
    public void i_add_users_to_the_project() {
        projectPage.addUserToProject();
    }
    @Then("I can see who is on the project")
    public void i_can_see_who_is_on_the_project() {
        projectPage.viewUsersOnProject();
    }

    @When("an issues {string} {string} and {string} are entered")
    public void an_issues_and_are_entered(String status, String priority, String severity) {
        projectPage.filterIssuesByStatusPriorityAndSeverity(status,priority,severity);
    }
    @Then("I can see all the filtered issues")
    public void i_can_see_all_the_filtered_issues() {
        projectPage.viewFilteredIssues();
    }
}