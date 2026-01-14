package com.example.RevaIssue.E2E.poms;

import com.example.RevaIssue.entity.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProjectPage extends ParentPOM {

    private final String URL = "http://localhost:4200/project";
    private final String URLLogin = "http://localhost:4200/login";

    // =========================
    // Page Elements
    // =========================

    @FindBy(className = "list-item-link")
    private WebElement firstProject;

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

    private Select severitynput;
    private Select priorityInput;

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

    public ProjectPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    //Need to log in and create a web token
    public void login(){
    @FindBy(css = ".issue-card")
    private List<WebElement> issueList;

    // =========================
    // Constructor
    // =========================

    public ProjectPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // =========================
    // Navigation / Setup
    // =========================

    public void login() {
        driver.get(URLLogin);
        driver.findElement(By.id("username")).sendKeys("tester@email.com");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("login-submit-btn")).click();
    }

    public void openProjectPage() {
        login();
        this.firstProject.click();
    }

    public void goToProject(int projectId) {
        driver.get(URL + projectId);
    }

    // =========================
    // Issue Selection Helpers
    // =========================

    // just get the first issue on the page to avoid having to search through the issueList
    public void selectFirstIssue() {
        issueList.getFirst().click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector(".issue-card.active")));
    }

    // click the update button on the issue you want to update
    public void clickUpdate() {
        WebElement firstIssue = driver.findElement(By.cssSelector(".issue-card.active"));
        firstIssue.findElement(By.cssSelector(".button_update button")).click();
    }

    // =========================
    // Issue Actions
    // =========================

    public void clickCreateIssue() {
        this.createIssueBtn.click();
    }

    public void filloutIssueInformation(String title, String desc, String severity, String priority){
        this.issueTitleInput.sendKeys(title);
        this.issueDescInput.sendKeys(desc);
        this.severitynput = new Select(severityDropdown);
        this.severitynput.selectByValue(severity);
        this.priorityInput = new Select(priorityDropdown);
        this.priorityInput.selectByValue(priority);
    }

    public void filloutUpdatedIssueInformation(String title, String desc, String severity, String priority){
        this.updateTitleInput.sendKeys(title);
        this.updateDescInput.sendKeys(desc);
        this.severitynput = new Select(updateSevDropdown);
        this.severitynput.selectByValue(severity);
        this.priorityInput = new Select(updatePriorDropdown);
        this.priorityInput.selectByValue(priority);
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
        this.updateIssueBtn.click();
    }

    public boolean isUpdateIssueOpen() {
        return this.updateIssueBtn.isDisplayed();
    }

    public void updateIssue(String title, String description, int severity, int priority) {
        // just get the first issue on the page to avoid having to search through the issueList
        selectFirstIssue();

        // click on the update button for the selected (first) issue
        clickUpdate();

        // update the fields of the Issue
        WebElement inputTitle = driver.findElement(By.cssSelector(".parent > input[type='text']"));
        inputTitle.sendKeys(title);

        WebElement inputDescription = driver.findElement(
                By.cssSelector(".parent textarea.description"));
        inputDescription.sendKeys(description);

        WebElement selectSeverity = driver.findElement(
                By.cssSelector(".parent select[name='severity']"));
        Select updatedSeverity = new Select(selectSeverity);
        updatedSeverity.selectByIndex(severity);

        WebElement selectPriority = driver.findElement(
                By.cssSelector(".parent select[name='priority']"));
        Select updatedPriority = new Select(selectPriority);
        updatedPriority.selectByIndex(priority);

        driver.findElement(
                By.cssSelector(".parent .issue_buttons button")).click();
    }

}
