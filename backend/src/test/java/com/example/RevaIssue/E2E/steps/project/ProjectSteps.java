package com.example.RevaIssue.E2E.steps.project;

import io.cucumber.java.PendingException;
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

}
