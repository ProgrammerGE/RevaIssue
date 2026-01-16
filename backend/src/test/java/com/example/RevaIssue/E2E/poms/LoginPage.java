package com.example.RevaIssue.E2E.poms;

import com.example.RevaIssue.E2E.driver.ChromeDriverManager;
import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.service.IssueService;
import com.example.RevaIssue.service.ProjectService;
import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String LOGIN_URL = "http://localhost:4200/login";
    private ProjectService projectService;
    private IssueService issueService;

    @FindBy(id = "login-username-input")
    private WebElement usernameInput;

    @FindBy(id = "login-password-input")
    private WebElement passwordInput;

    @FindBy(id = "login-submit-btn")
    private WebElement loginButton;

    @Autowired
    public LoginPage(ChromeDriverManager driverManager, ProjectService projectService, IssueService issueService) {
        this.driver = driverManager.getDriver();
        this.wait = driverManager.getWait();
        PageFactory.initElements(driver, this);
        this.projectService = projectService;
        this.issueService = issueService;
    }

    public void goToLogin() {
        driver.get(LOGIN_URL);
        // DOES THIS EVEN DO ANYTHING ?
        wait.until(d -> driver.findElement(By.tagName("app-login")));
    }

    public void enterUsername(String username) {
        this.wait.until(d -> usernameInput.isDisplayed());
        usernameInput.sendKeys(username);
    }

    public  void enterPassword(String password) {
        this.wait.until(d -> passwordInput.isDisplayed());
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(d -> loginButton.isDisplayed() && loginButton.isEnabled());
        loginButton.click();
    }

    public boolean isAtDashboard() {
        try {
            wait.until(d -> d.findElement(By.className("hub-page")).isDisplayed());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    //TODO: move to respective pom files
    public void goToHubpage() {
        driver.get("http://localhost:4200/hubpage");
    }

    public void createProject() {
        Project project = new Project();
        project.setProjectName("Test Project");
        project.setProjectDescription("Test Description");
        projectService.createProject(project);
    }

    public void createIssue() {
        Issue issue = new Issue();
        issue.setName("Test issue 1");
        issue.setDescription("Description for test issue 1");
        issue.setSeverity(1);
        issue.setPriority(2);
        issue.setStatus("open");
        Project p = projectService.getProjectById(1);
        issue.setProject(p);
        issueService.createIssue(issue);
    }
}
