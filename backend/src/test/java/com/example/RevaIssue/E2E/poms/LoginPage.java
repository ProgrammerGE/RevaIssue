package com.example.RevaIssue.E2E.poms;

import com.example.RevaIssue.E2E.driver.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginPage {
    private final String URL = "http://localhost:4200/hubpage";
    private final WebDriver driver;

    @Autowired
    public LoginPage(ChromeDriverManager chromeDriverManager){
        this.driver = chromeDriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }
}
