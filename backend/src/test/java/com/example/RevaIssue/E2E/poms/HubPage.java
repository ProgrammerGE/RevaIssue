package com.example.RevaIssue.E2E.poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * This POM file represents the Hubpage
 */
public class HubPage {

    private final String URL = "http://localhost:8080/hubpage";

    private final String URLLogin = "http://localhost:8080/login";

    private WebDriver driver;

    @FindBy(id = "delete_button_clickHere")
    private WebElement deleteButton;

    @FindBy(id = "delete_confirm")
    private WebElement deleteConfirm;

    @FindBy(className = "add-button")
    private WebElement createButton;

    @FindBy(id = "create_confirm")
    private WebElement confirmCreate;

    @FindBy(id = "update_button_clickHere")
    private WebElement updateButton;

    @FindBy(id = "update_confirm")
    private WebElement confirmUpdate;

    @FindBy(id = "proj_title")
    private WebElement projectTitleInput;

    @FindBy(id = "descriptionBox")
    private WebElement projectDescInput;

    @FindBy(partialLinkText = "Create Project")
    private WebElement submitBtn;

    public HubPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    //Need to log in and create a web token
    public void login(){
        driver.get(URLLogin);
        driver.findElement(By.id("username")).sendKeys("admin@email.com");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("login-submit-btn")).click();
    }

    public void openHubPage(){
        login();
        driver.get(URL);
    }

    public void clickDeleteProject(){
        this.deleteButton.click();
    }

    public void clickDeleteConfirm(){
        this.deleteConfirm.click();
    }

    public void submitUpdatedProject(){
        this.confirmUpdate.click();
    }

    public boolean isDeletePopupOpen(){
        return this.deleteButton.isDisplayed();
    }

    public void clickCreateProject(){
        this.createButton.click();
    }

    public boolean isCreatePopupOpen(){
        return this.createButton.isDisplayed();
    }

    public void enterInfo(String title, String description){
        projectTitleInput.sendKeys(title);
        projectDescInput.sendKeys(description);
    }

    public void submitNewProject(){
        this.submitBtn.click();
    }

    public void clickUpdateProject(){
        this.updateButton.click();
    }

    public boolean isUpdatePopupOpen(){
        return this.updateButton.isDisplayed();
    }

    public void cancelPopup(String popupTitle){
        driver.findElement(By.partialLinkText(popupTitle)).click();
    }
}
