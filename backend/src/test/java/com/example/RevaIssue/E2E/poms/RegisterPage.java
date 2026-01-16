package com.example.RevaIssue.E2E.poms;

import com.example.RevaIssue.E2E.driver.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;

public class RegisterPage {

    private final String URL = "http://localhost:4200/register";
    private final WebDriver driver;

    @FindBy(id = "user_input")
    private WebElement userNameInput;

    @FindBy(id = "password_input")
    private WebElement passwordInput;

    @FindBy(id = "confirm_password")
    private WebElement confirmPassword;

    @FindBy(tagName = "select")
    private WebElement roleDropdown;

    private Select roleInput;

    @FindBy(id = "register_accnt_button")
    private WebElement submitButton;

    @FindBy(id = "cancel_register_btn")
    private WebElement cancelButton;

    @Autowired
    public RegisterPage(ChromeDriverManager chromeDriverManager){
        this.driver = chromeDriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public void openRegistrationPage(){
        driver.get(URL);
    }

    public void registerUserInfo(String username, String password){
        this.userNameInput.sendKeys(username);
        this.passwordInput.sendKeys(password);
        this.confirmPassword.sendKeys(password);
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
