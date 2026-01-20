package com.example.RevaIssue.E2E.poms;

import com.example.RevaIssue.E2E.driver.ChromeDriverManager;
import com.example.RevaIssue.service.IssueService;
import com.example.RevaIssue.service.ProjectService;
import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
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
    private final WebDriver driver;
    private final WebDriverWait wait;
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
}
