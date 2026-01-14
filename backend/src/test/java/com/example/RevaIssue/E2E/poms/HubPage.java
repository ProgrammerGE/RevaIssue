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

    @FindBy(className = "add-button")
    private WebElement createButton;

    @FindBy(id = "update_button_clickHere")
    private WebElement updateButton;

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

    public void login(){
        driver.get(URLLogin);
        //Need to log in and create a web token
    }

    public void openHubPage(){
        login();
        driver.get(URL);
    }

    public void clickDeleteProject(){
        this.deleteButton.click();
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
