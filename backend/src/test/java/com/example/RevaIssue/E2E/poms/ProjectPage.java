package com.example.RevaIssue.E2E.poms;

import com.example.RevaIssue.E2E.driver.ChromeDriverManager;
import com.example.RevaIssue.E2E.helper.AuthHelper;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;
import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
@ScenarioScope
public class ProjectPage {

    private final String URL = "http://localhost:4200/projects";
    private final String URLLogin = "http://localhost:4200/login";
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final AuthHelper authHelper;

    // =========================
    // Page Elements
    // =========================

    private By firstProject = By.className("list-item-link");

    @FindBy(className = "btn-create")
    private WebElement createIssueBtn;

    @FindBy(id = "issue_title_inpt")
    private WebElement issueTitleInput;

    @FindBy(id = "descript_issue_inpt")
    private WebElement issueDescInput;

    @FindBy(name = "severity")
    private WebElement severityDropdown;

    @FindBy(name = "priority")
    private WebElement priorityDropdown;

    private Select severityInput;
    private Select priorityInput;

    @FindBy(name = "filter_status")
    private WebElement filterStatusDropdown;

    @FindBy(name = "filter_severity")
    private WebElement filterSeverityDropdown;

    @FindBy(name = "filter_priority")
    private WebElement filterPriorityDropdown;

    @FindBy(className = "btn-filter")
    private WebElement filterButton;

    @FindBy(id = "update_issue_btn")
    private WebElement updateIssueBtn;

    @FindBy(id = "issue_create_confirm")
    private WebElement confirmCreateIssue;

    @FindBy(id = "update_issue_confirm")
    private WebElement confirmUpdateIssue;

    @FindBy(id = "update_title_iss")
    private WebElement updateTitleInput;

    @FindBy(id = "update_desc_iss")
    private WebElement updateDescInput;

    @FindBy(id = "update_sev")
    private WebElement updateSevDropdown;

    @FindBy(id = "update_prior")
    private WebElement updatePriorDropdown;

    @FindBy(css = ".issue-card")
    private List<WebElement> issueList;

    private final By usernameInput = By.id("login-username-input");
    private final By passwordInput = By.id("login-password-input");

    // =========================
    // Constructor
    // =========================

    @Autowired
    public ProjectPage(ChromeDriverManager chromeDriverManager, AuthHelper authHelper){
        this.driver = chromeDriverManager.getDriver();
        this.wait = chromeDriverManager.getWait();
        this.authHelper = authHelper;
        PageFactory.initElements(driver, this);
    }

    // =========================
    // Navigation / Setup
    // =========================

    public void login(UserRole role) {
        driver.get(URLLogin);
        authHelper.authenticateUser(role);
    }

    public void openProjectPage(UserRole role) {
        login(role);
        driver.get("http://localhost:4200/hubpage");
        wait.until(ExpectedConditions.elementToBeClickable(firstProject)).click();
    }

    public boolean isOnProjectPage() {
        try {
            wait.until(ExpectedConditions.urlMatches(".*/projects.*"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void goToProject(int projectId) {
        driver.get(URL +"/"+ projectId);
    }

    public void openProjectPageAsRole(UserRole role){
        login(role);
        goToProject(1);
    }

    // =========================
    // Issue Selection Helpers
    // =========================

    // just get the first issue on the page to avoid having to search through the issueList
    public void selectFirstIssue() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".issue-card")));

        issueList.getFirst().click();
    }

    // =========================
    // Issue Actions
    // =========================

    public void clickCreateIssue() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(this.createIssueBtn));
        this.createIssueBtn.click();
    }

    public void filloutIssueInformation(String title, String desc, int severity, int priority){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".issue_title")));
        this.issueTitleInput.sendKeys(title);
        this.issueDescInput.sendKeys(desc);
        this.severityInput = new Select(severityDropdown);
        this.severityInput.selectByValue(String.valueOf(severity));
        this.priorityInput = new Select(priorityDropdown);
        this.priorityInput.selectByValue(String.valueOf(priority));
    }

    public void filloutUpdatedIssueInformation(String title, String desc, int severity, int priority){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".issue_title")));
        this.updateTitleInput.sendKeys(title);
        this.updateDescInput.sendKeys(desc);
        this.severityInput = new Select(updateSevDropdown);
        this.severityInput.selectByValue(String.valueOf(severity));
        this.priorityInput = new Select(updatePriorDropdown);
        this.priorityInput.selectByValue(String.valueOf(priority));
    }

    public void submitNewIssue(){
        this.confirmCreateIssue.click();
    }

    public void submitUpdatedIssue(){
        this.confirmUpdateIssue.click();
    }

    public boolean isCreateIssueOpen() {
        return this.createIssueBtn.isDisplayed();
    }

    public void clickUpdateIssue() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(this.updateIssueBtn));
        this.updateIssueBtn.click();
    }

    public boolean isUpdateIssueOpen() {
        try{
            return this.updateIssueBtn.isDisplayed();
        }catch (NoSuchElementException e){
            return true;
        }
    }

    public void updateIssue(String title, String description, int severity, int priority) {
        // just get the first issue on the page to avoid having to search through the issueList
        selectFirstIssue();

        clickUpdateIssue();

        filloutUpdatedIssueInformation(title,description,severity,priority);

        submitUpdatedIssue();
    }

    public void updateStatusIssueAsTester(String status) {
        openProjectPage(UserRole.TESTER);
        selectFirstIssue();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".comments-section")));

        if (status.equalsIgnoreCase("Open")){
                driver.findElement(By.id("reopen_isu_btn")).click();
            } else if (status.equalsIgnoreCase("Close")) {
                driver.findElement(By.id("close_isu_btn")).click();
            }
    }

    public void updateStatusIssueAsDeveloper(String status) {
        openProjectPage(UserRole.DEVELOPER);
        selectFirstIssue();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".comments-section")));
            if (status.equalsIgnoreCase("In Progress")){
                wait.until(ExpectedConditions.elementToBeClickable(By.id("in_progress_isu_btn"))).click();
            } else if (status.equalsIgnoreCase("Resolved")) {
                wait.until(ExpectedConditions.elementToBeClickable(By.id("resolv_isu_btn"))).click();
            }
    }

    public void filterIssuesByStatusPriorityAndSeverity(String status, String priority, String severity) {

        System.out.println("found filter issues header");
        WebDriverWait waitMore = new WebDriverWait(driver, Duration.ofSeconds(5));
        waitMore.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".filter_status")));
        System.out.println("found filter status");
        new Select(filterStatusDropdown).selectByVisibleText(status);
        new Select(filterPriorityDropdown).selectByValue(priority);
        new Select(filterSeverityDropdown).selectByValue(severity);
    }

    public void viewFilteredIssues() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".filter_btns")));
        this.filterButton.click();
    }

    // =========================
    // Project Actions
    // =========================

    public void addUserToProject() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".div3")));
        driver.findElement(By.id("add_user_to_project_input")).sendKeys("tester");
        driver.findElement(By.id("add_usr_btn")).click();
    }

    public void viewUsersOnProject() {
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".user-item")));
    }
}
