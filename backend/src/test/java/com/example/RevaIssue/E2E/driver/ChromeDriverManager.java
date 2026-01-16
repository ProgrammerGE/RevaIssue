package com.example.RevaIssue.E2E.driver;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import java.time.Duration;


@Component
@ScenarioScope
public class ChromeDriverManager {
    private WebDriver driver;
    private WebDriverWait wait;

    public WebDriver getDriver() {
        if (driver != null) {
            return driver;
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        return driver;
    }

    public WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        }
        return wait;
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
            wait = null;
        }
    }
}
