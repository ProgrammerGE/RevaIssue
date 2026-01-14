package com.example.RevaIssue.E2E.poms;

import org.openqa.selenium.WebDriver;

public class LoginPage extends ParentPOM{
    private final String URL = "http://localhost:4200/hubpage";
    public LoginPage(WebDriver driver) {
        super(driver);
    }
}
