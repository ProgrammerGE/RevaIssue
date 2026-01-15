package com.example.RevaIssue.E2E.steps.popups;

import io.cucumber.java.en.Given;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.projectPage;

public class TesterOnProjectPage {
    @Given("The user is on the project page")
    public void the_user_is_on_the_project_page() {
        projectPage.openProjectPage("tester");
    }
}
