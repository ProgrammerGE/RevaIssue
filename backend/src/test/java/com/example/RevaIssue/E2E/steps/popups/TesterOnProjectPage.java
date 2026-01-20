package com.example.RevaIssue.E2E.steps.popups;

import com.example.RevaIssue.E2E.poms.ProjectPage;
import com.example.RevaIssue.enums.UserRole;
import io.cucumber.java.en.Given;

public class TesterOnProjectPage {
    private final ProjectPage projectPage;

    public TesterOnProjectPage(ProjectPage projectPage) {
        this.projectPage = projectPage;
    }

    @Given("The user is on the project page")
    public void the_user_is_on_the_project_page() {
        projectPage.login(UserRole.TESTER);
        projectPage.openProjectPage();
    }
}
