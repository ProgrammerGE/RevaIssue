package com.example.RevaIssue.E2E.steps.popups.submitting;

import com.example.RevaIssue.E2E.poms.HubPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class SubmitCreateProject {
    private final HubPage hubPage;

    public SubmitCreateProject(HubPage hubPage) {
        this.hubPage = hubPage;
    }

    @When("The admin clicks on the create project button")
    public void the_admin_clicks_on_the_create_project_button(){
        this.hubPage.clickCreateProject();
    }
    @When("inputs the project details")
    public void inputs_the_project_details(){
        this.hubPage.enterInfo("New Project", "New Description");
    }
    @When("clicks the submit new project button")
    public void clicks_the_submit_new_project_button(){
        this.hubPage.submitNewProject();
    }
    @Then("A project will be created")
    public void a_project_will_be_created() {
        Assertions.assertTrue(this.hubPage.isCreatePopupOpen());
    }
}
