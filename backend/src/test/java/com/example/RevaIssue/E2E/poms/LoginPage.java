package com.example.RevaIssue.E2E.poms;

import com.example.RevaIssue.E2E.driver.ChromeDriverManager;
import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String LOGIN_URL = "http://localhost:4200/login";

    @FindBy(id = "login-username-input")
    private WebElement usernameInput;

    @FindBy(id = "login-password-input")
    private WebElement passwordInput;

    @FindBy(id = "login-submit-btn")
    private WebElement loginButton;

    @Autowired
    public LoginPage(ChromeDriverManager driverManager) {
        this.driver = driverManager.getDriver();
        System.out.println(driver);
        this.wait = driverManager.getWait();
        PageFactory.initElements(driver, this);
    }

    public void goToLogin() {
        driver.get(LOGIN_URL);
        wait.until(d -> driver.findElement(By.tagName("app-login")));
    }

    public void enterUsername(String username) {
        this.wait.until(d -> usernameInput.isDisplayed());
        usernameInput.sendKeys(username);
    }

    public  void enterPassword(String password) {
        this.wait.until(d -> passwordInput.isDisplayed());
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(d -> loginButton.isDisplayed() && loginButton.isEnabled());
        loginButton.click();
    }

    public boolean isAtDashboard() {
        try {
            wait.until(d -> d.findElement(By.className("hub-page")).isDisplayed());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
