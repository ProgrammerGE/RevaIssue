package com.example.RevaIssue.E2E.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.hubpage;

public class PopupEditProject {
    @Given("The admin is on the hub page")
    public void the_admin_is_on_the_hub_page() {
        hubpage.openHubPage();
    }
    @When("The admin clicks on the edit project button")
    public void the_admin_clicks_on_the_edit_project_button() {
        hubpage.clickUpdateProject();
    }
    @Then("A popup will appear to edit the selected project")
    public void a_popup_will_appear_to_edit_the_selected_project() {
        Assertions.assertTrue(hubpage.isUpdatePopupOpen());
    }
}
