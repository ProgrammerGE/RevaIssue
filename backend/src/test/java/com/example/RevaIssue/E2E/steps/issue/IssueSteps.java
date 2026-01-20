package com.example.RevaIssue.E2E.steps.issue;

import com.example.RevaIssue.E2E.poms.ProjectPage;
import com.example.RevaIssue.enums.UserRole;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class IssueSteps {
    private final ProjectPage projectPage;
    private UserRole currentRole;

    public IssueSteps(ProjectPage projectPage) {
        this.projectPage = projectPage;
    }

    // Background
    @Given("I open a project with issues")
    public void i_open_a_project_with_issues() {
//        projectPage.logout();projectPage
        projectPage.login(UserRole.ADMIN);
        projectPage.openProjectPage();
    }

    // Scenario: View list of issues
    @Then("I will see a list of issues for that project")
    public void i_will_see_a_list_of_issues_for_that_project() {
        projectPage.goToProject(1);
    }

    // Scenario: Update an issue
    @When("I select an issue I want to update")
    public void i_select_an_issue_i_want_to_update() {
        projectPage.selectFirstIssue();
    }
    @When("I change the values of the issue")
    public void i_change_the_values_of_the_issue() {
        projectPage.updateIssue("New title", "New desc", 2, 2);
    }
    @Then("I will see the updated issue details")
    public void i_will_see_the_updated_issue_details() {
        projectPage.selectFirstIssue();
    }

    // Scenario: View history of an issue
    @When("I select an issue")
    public void i_select_an_issue() {
        projectPage.selectFirstIssue();
    }
    @Then("I can see the comments describing the history")
    public void i_can_see_the_comments_describing_the_history() {
        projectPage.selectFirstIssue();
    }

//     Scenario Outline: Change Issue Status
@Given("I am logged in as {string}")
public void iAmLoggedInAs(String role) {
    currentRole = UserRole.valueOf(role.toUpperCase());
    projectPage.logout();
    projectPage.login(currentRole);
}

    @When("I set the issue status to {string}")
    public void i_set_the_issue_status_to(String status){
        if (currentRole == UserRole.TESTER){
            projectPage.updateStatusIssueAsTester(status);
        } else if (currentRole == UserRole.DEVELOPER) {
            projectPage.updateStatusIssueAsDeveloper(status);
        }
    }

    @Then("the issue status is now {string}")
    public void the_issue_status_is_now(String status){
        projectPage.selectFirstIssue();
    }

}
