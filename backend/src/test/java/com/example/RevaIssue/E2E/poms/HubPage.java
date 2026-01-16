package com.example.RevaIssue.E2E.poms;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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

    WebElement deletedProject;

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
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                (ExpectedConditions.elementToBeClickable(By.className("list-item-link"))));
        this.deletedProject = driver.findElement(By.className("list-item-link"));
        new Actions(driver)
                .moveToElement(deletedProject)
                .pause(Duration.ofSeconds(5))
                .contextClick()
                .pause(Duration.ofSeconds(5))
                .perform();
        driver.findElement(By.id("delete-button-clickHere")).click();
    }

    public void clickDeleteConfirm(){
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("delete_confirm")));
        driver.findElement(By.id("delete_confirm")).click();
    }

    public void submitUpdatedProject(){
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("update_confirm")));
        driver.findElement(By.id("update_confirm")).click();
    }

    public boolean isDeletePopupOpen(){
        try{
            return driver.findElement(By.id("delete_confirm")).isDisplayed();
        }catch (Exception e){
            return true;
        }
    }

    public void clickCreateProject(){
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".add-button")));
        driver.findElement(By.cssSelector(".add-button")).click();
    }

    public boolean isCreatePopupOpen(){
        try{
            return driver.findElement(By.cssSelector(".add-button")).isDisplayed();
        }catch (NoSuchElementException e){
            return true;
        }

    }

    public void enterInfo(String title, String description){
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.className("proj_name")));
        driver.findElement(By.id("proj_title")).sendKeys(title);
        driver.findElement(By.id("descriptionBox")).sendKeys(description);
    }

    public void submitNewProject(){
        driver.findElement(By.id("create_confirm")).click();
    }

    public void clickUpdateProject(){
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                (ExpectedConditions.elementToBeClickable(By.className("list-item-link"))));
        WebElement firstProject = driver.findElement(By.className("list-item-link"));
        new Actions(driver)
                .moveToElement(firstProject)
                .pause(Duration.ofSeconds(1))
                .contextClick()
                .pause(Duration.ofSeconds(1))
                .perform();
        driver.findElement(By.id("update-button-clickHere")).click();
    }

    public boolean isUpdatePopupOpen(){
        try{
            return driver.findElement(By.id("update-button-clickHere")).isDisplayed();
        }catch (NoSuchElementException e){
            return true;
        }
    }

    public void openPopup(String popupTitle){
        if(popupTitle.equalsIgnoreCase("Delete Project")){
            clickDeleteProject();
        } else if (popupTitle.equalsIgnoreCase("Create Project")) {
            clickCreateProject();
        } else if (popupTitle.equalsIgnoreCase("Update Project")) {
            clickUpdateProject();
        }
    }

    public void cancelPopup(String popupTitle){
        driver.findElement(By.id("cancel_btn")).click();
    }
}
