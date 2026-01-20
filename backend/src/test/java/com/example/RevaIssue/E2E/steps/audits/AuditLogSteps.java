package com.example.RevaIssue.E2E.steps.audits;

import com.example.RevaIssue.E2E.poms.HubPage;
import com.example.RevaIssue.E2E.poms.ProjectPage;
import com.example.RevaIssue.enums.UserRole;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class AuditLogSteps {
    private final HubPage hubPage;
    private final ProjectPage projectPage;

    public AuditLogSteps(HubPage hubPage, ProjectPage projectPage) {
        this.hubPage = hubPage;
        this.projectPage = projectPage;
    }

    @When("the admin looks at the audit log section")
    public void the_admin_looks_at_the_audit_log_section() {
        Assertions.assertTrue(this.hubPage.searchForAuditSection());
    }

    @Then("they can see the audit log list within the section")
    public void they_can_see_the_audit_log_list_within_the_section() {
        System.out.println("The Audit logs are listed.");
    }

    /**
     * =================================================================
     *          Negative Test
     * =================================================================
     */
    @Given("The {string} is on the hub page")
    public void the_is_on_the_hub_page(String role) {
        if (role.equalsIgnoreCase("tester"))
            hubPage.openHubPageNonAdmin(UserRole.TESTER);
        else if (role.equalsIgnoreCase("developer")) {
            hubPage.openHubPageNonAdmin(UserRole.DEVELOPER);
        }
    }
    @When("they look for the audit log section")
    public void they_look_for_the_audit_log_section() {
        Assertions.assertFalse(this.hubPage.searchForAuditSection());
    }
    @Then("they can't see the audit log list")
    public void they_can_t_see_the_audit_log_list() {
        System.out.println("The audit log isn't visible.");
    }
}
