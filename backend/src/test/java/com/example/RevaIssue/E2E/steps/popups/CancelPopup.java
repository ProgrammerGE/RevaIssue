package com.example.RevaIssue.E2E.steps.popups;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import static com.example.RevaIssue.E2E.fixtures.FixtureResources.hubpage;

public class CancelPopup {
    @When("The admin clicks on the cancel button on the {string} popup")
    public void the_admin_clicks_on_the_cancel_button_on_the_popup(String title) {
        hubpage.cancelPopup(title);
    }
    @Then("The {string} popup will disappear")
    public void the_popup_will_disappear(String title) {
        if(title.equals("Delete Project")){
            Assertions.assertTrue(hubpage.isDeletePopupOpen());
        }
        else if(title.equals("Create Project")){
            Assertions.assertTrue(hubpage.isCreatePopupOpen());
        }
        else if(title.equals("Update Project")){
            Assertions.assertTrue(hubpage.isUpdatePopupOpen());
        }
    }
}
