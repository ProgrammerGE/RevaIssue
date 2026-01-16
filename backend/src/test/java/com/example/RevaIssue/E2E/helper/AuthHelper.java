package com.example.RevaIssue.E2E.helper;

import com.example.RevaIssue.E2E.driver.ChromeDriverManager;
import com.example.RevaIssue.dto.LoginRequest;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.service.AuthService;
import com.example.RevaIssue.service.UserService;
import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@ScenarioScope
public class AuthHelper {
    private final WebDriver driver;
    private final UserService userService;
    private final AuthService authService;


    @Autowired
    public AuthHelper(ChromeDriverManager driverManager, UserService userService, AuthService authService) {
        this.driver = driverManager.getDriver();
        this.userService = userService;
        this.authService = authService;
    }

    public void authenticateUser(UserRole role) {
        User user = new User();
        user.setUsername("user@mail.com");
        user.setPassword("#@!user-strong-password!@#");
        user.setUserRole(role);
        userService.createUser(user);
        LoginRequest request = new LoginRequest(user.getUsername(), user.getPassword());
        String token = authService.login(request);
        ((JavascriptExecutor) driver).executeScript(
                "window.localStorage.setItem('REVAISSUE_TOKEN', arguments[0]);",
                token
        );
    }
}
