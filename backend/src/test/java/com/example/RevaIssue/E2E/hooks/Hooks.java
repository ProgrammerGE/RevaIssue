package com.example.RevaIssue.E2E.hooks;

import com.example.RevaIssue.E2E.driver.ChromeDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class Hooks {
    @Autowired
    ChromeDriverManager driverManager;

    @Before
    public void setupDriver() {
        driverManager.getDriver();
    }

    @After
    public void teardownDriver() {
        driverManager.quit();
    }
}

