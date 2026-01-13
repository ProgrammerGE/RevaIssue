package com.example.RevaIssue.E2E.steps.popups.open_close;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.hubpage;

public class PopupCreateProject {
    @Given("The admin is on the hub page")
    public void the_admin_is_on_the_hub_page() {
        hubpage.openHubPage();
    }
    @When("The admin clicks on the create project button")
    public void the_admin_clicks_on_the_create_project_button() {
        hubpage.clickCreateProject();
    }
    @Then("A popup will appear to create a new project")
    public void a_popup_will_appear_to_create_a_new_project() {
        Assertions.assertTrue(hubpage.isCreatePopupOpen());
    }
}
