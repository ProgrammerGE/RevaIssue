package com.example.RevaIssue.E2E.poms;

import com.example.RevaIssue.E2E.driver.ChromeDriverManager;
import com.example.RevaIssue.E2E.helper.AuthHelper;
import com.example.RevaIssue.enums.UserRole;
import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Duration;
import org.springframework.stereotype.Component;

/**
 * This POM file represents the Hubpage
 */
@Component
@ScenarioScope
public class HubPage {

    private final String URL = "http://localhost:4200/hubpage";
    private final String URLLogin = "http://localhost:4200/login";

    private final WebDriver driver;
  
    private WebElement deletedProject;
    private  WebDriverWait wait;
    private final AuthHelper authHelper;


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

    @FindBy(id = "searchbar")
    private WebElement searchbar;

    @FindBy(id = "issue-search-input")
    private WebElement issueSearchInput;

    @Autowired
    public HubPage(ChromeDriverManager chromeDriverManager, AuthHelper authHelper){
        this.driver = chromeDriverManager.getDriver();
        this.wait = chromeDriverManager.getWait();
        this.authHelper = authHelper;
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
        driver.get(URLLogin);
         this.authHelper.authenticateUser(UserRole.ADMIN);
         driver.get(URL);
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

    public void toggleSearchPopup() {
        this.wait.until(ExpectedConditions.elementToBeClickable(this.searchbar));
        this.searchbar.click();
    }

    public void searchForIssue() {
        this.wait.until(ExpectedConditions.elementToBeClickable(this.issueSearchInput));
        this.issueSearchInput.sendKeys("First Issue");
    }

    public void selectIssueSearchResult() {
        WebElement searchResult = this.wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.search-result-item")));
        searchResult.click();
    }

    public boolean isOnHubpage() {
        try {
            wait.until(d -> d.findElement(By.className("hub-page")).isDisplayed());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}


