package com.example.RevaIssue.E2E.steps.search;

import com.example.RevaIssue.E2E.helper.AuthHelper;
import com.example.RevaIssue.E2E.poms.HubPage;
import com.example.RevaIssue.E2E.poms.LoginPage;
import com.example.RevaIssue.E2E.poms.ProjectPage;
import com.example.RevaIssue.enums.UserRole;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.ScenarioScope;

@ScenarioScope
public class SearchIssues {
    private final LoginPage loginPage;
    private final ProjectPage projectPage;
    private final HubPage hubPage;
    private final AuthHelper authHelper;

    public SearchIssues (LoginPage loginPage, HubPage hubPage, AuthHelper authHelper, ProjectPage projectPage) {
        this.loginPage = loginPage;
        this.hubPage = hubPage;
        this.authHelper = authHelper;
        this.projectPage = projectPage;
    }

    @Given("The user is logged into the hubpage")
    public void theUserIsLoggedIn() {
        this.loginPage.goToLogin();
        authHelper.authenticateUser(UserRole.TESTER);
        this.hubPage.openHubPage();
    }

    @When("The user searches for an issue")
    public void theUserCanSearchForAnIssue() {
        this.hubPage.toggleSearchPopup();
        this.hubPage.searchForIssue();
    }

    @And("The user clicks on a result")
    public void theUserClicksOnAResult() {
        this.hubPage.selectIssueSearchResult();
    }

    @Then("The user is sent to the issue's project page")
    public void theUserIsSentToIssuesProjectPage() {
        boolean onProjectPage = this.projectPage.isOnProjectPage();
        if (!onProjectPage) {
            throw new AssertionError("redirect to project page failed");
        }
    }
}
