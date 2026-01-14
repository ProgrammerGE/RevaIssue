package com.example.RevaIssue.E2E.fixtures;

import com.example.RevaIssue.E2E.poms.HubPage;
import com.example.RevaIssue.E2E.poms.ProjectPage;
import com.example.RevaIssue.E2E.poms.RegisterPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class FixtureResources {
    public static WebDriver driver;
    public static HubPage hubpage;
    public static RegisterPage registerPage;
    public static ProjectPage projectPage;

    @Before
    public static void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        hubpage = new HubPage(driver);
        registerPage = new RegisterPage(driver);
        projectPage = new ProjectPage(driver);
    }

    @After
    public static void tearDown(){
        if(driver != null)
            driver.quit();
    }
}
