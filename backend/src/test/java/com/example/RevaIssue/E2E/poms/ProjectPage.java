package com.example.RevaIssue.E2E.poms;

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

    private final String URL = "http://localhost:4200/projects";
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

    private Select severityInput;
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

    @FindBy(css = ".issue-card")
    private List<WebElement> issueList;

    private final By usernameInput = By.id("login-username-input");
    private final By passwordInput = By.id("login-password-input");

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

    public void login(String role) {
        driver.get(URLLogin);
        if(role.equalsIgnoreCase("admin")) {
            driver.findElement(usernameInput).sendKeys("admin");
            driver.findElement(passwordInput).sendKeys("admin");
        } else if (role.equalsIgnoreCase("tester")) {
            driver.findElement(usernameInput).sendKeys("tester");
            driver.findElement(passwordInput).sendKeys("tester");
        } else if (role.equalsIgnoreCase("developer")) {
            driver.findElement(usernameInput).sendKeys("dev");
            driver.findElement(passwordInput).sendKeys("dev");
        }
        driver.findElement(By.id("login-submit-btn")).click();
    }

    public void openProjectPage(String role) {
        login(role);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(firstProject)).click();
    }

    public void goToProject(int projectId) {
        driver.get(URL +"/"+ projectId);
    }

    public void openProjectPageAsRole(String role){
        if (role.equalsIgnoreCase("tester")){
            driver.get(URLLogin);
            driver.findElement(By.id("username")).sendKeys("tester@email.com");
            driver.findElement(By.id("password")).sendKeys("password");
            driver.findElement(By.id("login-submit-btn")).click();
        } else if (role.equalsIgnoreCase("developer")) {
            driver.get(URLLogin);
            driver.findElement(By.id("username")).sendKeys("developer@email.com");
            driver.findElement(By.id("password")).sendKeys("password");
            driver.findElement(By.id("login-submit-btn")).click();
        }
        goToProject(1);
    }

    // =========================
    // Issue Selection Helpers
    // =========================

    // just get the first issue on the page to avoid having to search through the issueList
    public void selectFirstIssue() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".issue-card")));

        issueList.getFirst().click();
    }

    // =========================
    // Issue Actions
    // =========================

    public void clickCreateIssue() {
        this.createIssueBtn.click();
    }

    public void filloutIssueInformation(String title, String desc, int severity, int priority){
        this.issueTitleInput.sendKeys(title);
        this.issueDescInput.sendKeys(desc);
        this.severityInput = new Select(severityDropdown);
        this.severityInput.selectByValue(String.valueOf(severity));
        this.priorityInput = new Select(priorityDropdown);
        this.priorityInput.selectByValue(String.valueOf(priority));
    }

    public void filloutUpdatedIssueInformation(String title, String desc, int severity, int priority){
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
        this.updateIssueBtn.click();
    }

    public boolean isUpdateIssueOpen() {
        return this.updateIssueBtn.isDisplayed();
    }

    public void updateIssue(String title, String description, int severity, int priority) {
        // just get the first issue on the page to avoid having to search through the issueList
        selectFirstIssue();

        clickUpdateIssue();

        filloutUpdatedIssueInformation(title,description,severity,priority);

        submitUpdatedIssue();
    }

    public void updateStatusIssueAsTester(String status) {
        openProjectPage("tester");
        selectFirstIssue();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".comments-section")));

        if (status.equalsIgnoreCase("Open")){
                driver.findElement(By.id("reopen_isu_btn")).click();
            } else if (status.equalsIgnoreCase("Close")) {
                driver.findElement(By.id("close_isu_btn")).click();
            }
    }

    public void updateStatusIssueAsDeveloper(String status) {
        openProjectPage("developer");
        selectFirstIssue();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".comments-section")));
            if (status.equalsIgnoreCase("In Progress")){
                driver.findElement(By.id("in_progress_isu_btn")).click();
            } else if (status.equalsIgnoreCase("Resolved")) {
                driver.findElement(By.id("resolv_isu_btn")).click();
            }
    }
}
