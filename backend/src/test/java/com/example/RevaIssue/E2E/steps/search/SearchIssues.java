package com.example.RevaIssue.E2E.steps.search;

import com.example.RevaIssue.E2E.helper.AuthHelper;
import com.example.RevaIssue.E2E.poms.HubPage;
import com.example.RevaIssue.E2E.poms.LoginPage;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.service.AuthService;
import com.example.RevaIssue.service.ProjectService;
import com.example.RevaIssue.service.UserService;
import com.example.RevaIssue.util.JwtUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;

@ScenarioScope
public class SearchIssues {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    JwtUtility jwt;
    @Autowired
    AuthService authService;
    @Autowired
    ProjectService projectService;
    @Autowired
    LoginPage loginPage;
    @Autowired
    HubPage hubPage;
    @Autowired
    AuthHelper authHelper;

    @Given("The user is logged in")
    public void theUserIsLoggedIn() throws InterruptedException {
        this.loginPage.goToLogin();
        authHelper.authenticateUser(UserRole.TESTER);
        this.loginPage.goToHubpage();
        this.loginPage.createProject();
        this.loginPage.createIssue();
//        Thread.sleep(20000);
    }

    @Then("The user can search for an issue")
    public void theUserCanSearchForAnIssue() throws InterruptedException {
        this.hubPage.enableSearchPopup();
        Thread.sleep(200000);
    }
//
//    @Then("The user can search for an issue")
//    public void theUserCanSearch() throws InterruptedException {
//        WebElement searchBar = driver.findElement(By.id("searchbar"));
//        searchBar.click();
//        WebElement searchPopup = driver.findElement(By.cssSelector("div.search-container > input"));
//        searchPopup.sendKeys("Test");
//        Thread.sleep(100000);
//    }
}
