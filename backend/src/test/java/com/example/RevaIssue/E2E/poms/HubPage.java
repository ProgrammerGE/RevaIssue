package com.example.RevaIssue.E2E.poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * This POM file represents the Hubpage
 */
public class HubPage extends ParentPOM {

    private final String URL = "http://localhost:4200/hubpage";

    private final String URLLogin = "http://localhost:4200/login";

    public HubPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }
    //Need to log in and create a web token
    public void login(){
        driver.get(URLLogin);
        driver.findElement(By.id("login-username-input")).sendKeys("admin");
        driver.findElement(By.id("login-password-input")).sendKeys("admin");
        driver.findElement(By.id("login-submit-btn")).click();
    }

    public void openHubPage(){
        login();
        //driver.get(URL);
    }

    public void clickDeleteProject(){
        driver.findElement(By.id("delete_button_clickHere")).click();
    }

    public void clickDeleteConfirm(){
        driver.findElement(By.id("delete_confirm")).click();
    }

    public void submitUpdatedProject(){
        driver.findElement(By.id("update_confirm")).click();
    }

    public boolean isDeletePopupOpen(){
        return driver.findElement(By.id("delete_button_clickHere")).isDisplayed();
    }

    public void clickCreateProject(){
        driver.findElement(By.className("add-button")).click();
    }

    public boolean isCreatePopupOpen(){
        return driver.findElement(By.id("create_projec_btn")).isDisplayed();
    }

    public void enterInfo(String title, String description){
        /*WebElement firstProject = driver.findElement(By.className("proj_name"));
        new Actions(driver)
                .moveToElement(firstProject)
                .pause(Duration.ofSeconds(1))
                .moveToElement(driver.findElement(By.id("proj_title")))
                .pause(Duration.ofSeconds(1))
                .sendKeys(title)
                .pause(Duration.ofSeconds(1))
                .moveToElement(driver.findElement(By.id("descriptionBox")))
                .pause(Duration.ofSeconds(1))
                .sendKeys(description)
                .perform();*/
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.className("proj_name")));
        driver.findElement(By.id("proj_title")).sendKeys(title);
        driver.findElement(By.id("descriptionBox")).sendKeys(description);
    }

    public void submitNewProject(){
        driver.findElement(By.partialLinkText("Create Project")).click();
    }

    public void clickUpdateProject(){
        driver.findElement(By.id("update_button_clickHere")).click();
    }

    public boolean isUpdatePopupOpen(){
        return driver.findElement(By.id("update_button_clickHere")).isDisplayed();
    }

    public void cancelPopup(String popupTitle){
        driver.findElement(By.partialLinkText(popupTitle)).click();
    }
}
