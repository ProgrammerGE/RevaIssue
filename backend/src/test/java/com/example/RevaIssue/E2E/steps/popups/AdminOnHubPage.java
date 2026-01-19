package com.example.RevaIssue.E2E.steps.popups;
import com.example.RevaIssue.E2E.poms.HubPage;
import io.cucumber.java.en.Given;

public class AdminOnHubPage {
    private final HubPage hubPage;

    public AdminOnHubPage(HubPage hubPage) {
        this.hubPage = hubPage;
    }

    @Given("The admin is on the hub page")
    public void the_admin_is_on_the_hub_page() {
        this.hubPage.openHubPage();
    }
}
