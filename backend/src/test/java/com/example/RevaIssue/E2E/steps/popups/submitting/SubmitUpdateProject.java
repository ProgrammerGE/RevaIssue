package com.example.RevaIssue.E2E.steps.popups.submitting;

import com.example.RevaIssue.E2E.poms.HubPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class SubmitUpdateProject {
    private final HubPage hubPage;

    public SubmitUpdateProject(HubPage hubPage) {
        this.hubPage = hubPage;
    }

    @When("The admin clicks on the update project button")
    public void the_admin_clicks_on_the_update_project_button(){
        this.hubPage.clickUpdateProject();
    }

    @When("inputs the updated project details")
    public void inputs_the_updated_project_details(){
        hubPage.enterInfo("Updated Project", "Updated Description");
    }
    @When("clicks the submit update project button")
    public void clicks_the_submit_update_project_button(){
        hubPage.submitUpdatedProject();
    }

    @Then("A project will be updated")
    public void a_project_will_be_updated() {
        Assertions.assertTrue(hubPage.isUpdatePopupOpen());
    }
}
