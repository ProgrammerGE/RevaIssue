package com.example.RevaIssue.E2E.poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage {

    private final String URL = "http://localhost:4200/register";

    private WebDriver driver;

    @FindBy(id = "user_input")
    private WebElement userNameInput;

    @FindBy(id = "password_input")
    private WebElement passwordInput;

    @FindBy(tagName = "select")
    private WebElement roleDropdown;

    private Select roleInput;

    @FindBy(partialLinkText = "Register")
    private WebElement submitButton;

    @FindBy(partialLinkText = "Cancel")
    private WebElement cancelButton;

    public RegisterPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void openRegistrationPage(){
        driver.get(URL);
    }

    public void registerUserInfo(String username, String password){
        this.userNameInput.sendKeys(username);
        this.passwordInput.sendKeys(password);
    }

    public void registerRoleInfo(String role){
        this.roleInput = new Select(roleDropdown);
        this.roleInput.selectByValue(role);
    }

    public void submitRegistration(){
        this.submitButton.click();
    }

    public void cancelRegistration(){
        this.cancelButton.click();
    }
}
