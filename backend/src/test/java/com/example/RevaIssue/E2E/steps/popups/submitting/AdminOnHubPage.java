package com.example.RevaIssue.E2E.steps.popups.submitting;

import io.cucumber.java.en.Given;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.hubpage;

public class AdminOnHubPage {
    @Given("The admin is on the hub page")
    public void the_admin_is_on_the_hub_page() {
        hubpage.openHubPage();
    }
}
