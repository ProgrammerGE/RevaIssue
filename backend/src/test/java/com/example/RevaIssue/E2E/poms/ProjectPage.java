package com.example.RevaIssue.E2E.poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ProjectPage {
    private final String URL = "http://localhost:8080/project";

    private final String URLLogin = "http://localhost:8080/login";

    private WebDriver driver;

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
        driver.get(URLLogin);
        driver.findElement(By.id("username")).sendKeys("tester@email.com");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("login-submit-btn")).click();
    }

    public void openProjectPage(){
        login();
        this.firstProject.click();
    }

    public void clickCreateIssue(){
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

    public boolean isCreateIssueOpen(){
        return this.createIssueBtn.isDisplayed();
    }

    public void clickUpdateIssue(){
        this.updateIssueBtn.click();
    }

    public boolean isUpdateIssueOpen(){
        return this.updateIssueBtn.isDisplayed();
    }
}
