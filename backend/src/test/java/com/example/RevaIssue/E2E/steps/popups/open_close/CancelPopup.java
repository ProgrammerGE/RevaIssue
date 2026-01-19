package com.example.RevaIssue.E2E.steps.popups.open_close;

import com.example.RevaIssue.E2E.poms.HubPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class CancelPopup {
    private final HubPage hubPage;

    public CancelPopup(HubPage hubPage) {
        this.hubPage = hubPage;
    }

    @When("The admin clicks on the {string} project button")
    public void the_admin_clicks_on_the_project_button(String title) {
        hubPage.openPopup(title);
    }

    @When("The admin clicks on the cancel button on the {string} popup")
    public void the_admin_clicks_on_the_cancel_button_on_the_popup(String title) {
        hubPage.cancelPopup(title);
    }

    @Then("The {string} popup will disappear")
    public void the_popup_will_disappear(String title) {
        if(title.equals("Delete Project")){
            Assertions.assertTrue(hubPage.isDeletePopupOpen());
        }
        else if(title.equals("Create Project")){
            Assertions.assertTrue(hubPage.isCreatePopupOpen());
        }
        else if(title.equals("Update Project")){
            Assertions.assertTrue(hubPage.isUpdatePopupOpen());
        }
    }
}
