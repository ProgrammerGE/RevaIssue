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
    private static final String TEST_USERNAME = "june@email.com";
    private static final String TEST_PASSWORD = "062026";

    @Before()
    public void setup() {
        if(userRepository.findByUsername(TEST_USERNAME).isEmpty()) {
            User user = new User();
            user.setUsername(TEST_USERNAME);
            user.setPassword(TEST_PASSWORD);
            user.setUserRole(UserRole.ADMIN);
            userService.createUser(user);
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @Given("The admin is on the login page")
    public void the_user_is_in_the_login_page() {
        driver.get(LOGIN_URL);
        wait.until(d -> driver.findElement(By.tagName("app-login")));
    }

    @When("They enter a valid username")
    public void theyEnterAValidUsername() {
        WebElement usernameField = driver.findElement(By.id("login-username-input"));
        wait.until(d -> usernameField.isDisplayed());
        usernameField.sendKeys(TEST_USERNAME);
    }

    @And("They enter a valid password")
    public void theyEnterValidPassword() {
        WebElement passwordField = driver.findElement(By.id("login-password-input"));
        wait.until(d -> passwordField.isDisplayed());
        passwordField.sendKeys(TEST_PASSWORD);
    }

    @And("They click the login button")
    public void theyClickTheLoginButton() {
        driver.findElement(By.id("login-submit-btn")).click();
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

    @After()
    public void cleanup() {
        if(driver!=null){
            driver.quit();
        }
    }
}
