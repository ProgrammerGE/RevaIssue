package com.example.RevaIssue.E2E.poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProjectPage {
    private final String URL = "http://localhost:8080/project";

    private final String URLLogin = "http://localhost:8080/login";

    private WebDriver driver;

    @FindBy(className = "list-item-link")
    private WebElement firstProject;

    @FindBy(className = "btn-create")
    private WebElement createIssueBtn;

    @FindBy(id = "update_issue_btn")
    private WebElement updateIssueBtn;

    public ProjectPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(){
        driver.get(URLLogin);
        //Need to log in and create a web token
    }

    public void openProjectPage(){
        login();
        this.firstProject.click();
    }

    public void clickCreateIssue(){
        this.createIssueBtn.click();
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
