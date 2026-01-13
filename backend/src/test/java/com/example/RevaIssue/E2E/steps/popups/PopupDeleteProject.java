package com.example.RevaIssue.E2E.steps.popups;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.hubpage;

public class PopupDeleteProject {
    @Given("The admin is on the hub page")
    public void the_admin_is_on_the_hub_page() {
        hubpage.openHubPage();
    }
    @When("The admin clicks on the delete project button")
    public void the_admin_clicks_on_the_delete_project_button() {
        hubpage.clickDeleteProject();
    }
    @Then("A popup will appear for deleting a project")
    public void a_popup_will_appear_for_deleting_a_project() {
        Assertions.assertTrue(hubpage.isDeletePopupOpen());
    }
}
