package com.example.RevaIssue.E2E.steps;

import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.service.UserService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Duration;

public class LoginSteps {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String LOGIN_URL =  "http://localhost:4200";

    @Before()
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @Given("A user exists with username {string} and password {string}")
    public void aUserExists(String username, String password) {
        if(userRepository.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setUserRole(UserRole.TESTER);
            userService.createUser(user);
        }
    }

    @And("The user is on the login page")
    public void theUserIsOnLoginPage() {
        driver.get(LOGIN_URL);
        wait.until(d -> driver.findElement(By.tagName("app-login")));
    }

    @When("They enter a username {string}")
    public void theyEnterUsername(String username) {
        WebElement usernameField = driver.findElement(By.id("login-username-input"));
        wait.until(d -> usernameField.isDisplayed());
        usernameField.sendKeys(username);
    }

    @And("They enter a password {string}")
    public void theyEnterPassword(String password) {
        WebElement passwordField = driver.findElement(By.id("login-password-input"));
        wait.until(d -> passwordField.isDisplayed());
        passwordField.sendKeys(password);
    }

    @And("They click the login button")
    public void theyClickTheLoginButton() {
        WebElement loginBtn = driver.findElement(By.id("login-submit-btn"));
        if (loginBtn.isEnabled()) {
            loginBtn.click();
        }
        else {
            System.out.println("The login button is disabled!");
        }
    }

    @Then("They are directed to the hubpage")
    public void theyAreDirectedToTheHubpage () {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("hub-page")
                )
        );
        System.out.println("User logged in");
    }

    @Then("They remain on the login page")
    public void theyRemainOnLoginPage () {
        this.theUserIsOnLoginPage();
    }

    @After()
    public void cleanup() {
        if(driver!=null){
            driver.quit();
        }
    }
}
