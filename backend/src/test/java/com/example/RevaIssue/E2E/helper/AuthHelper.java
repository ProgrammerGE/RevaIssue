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
import org.springframework.web.server.ResponseStatusException;


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

//    public void authenticateUser(UserRole role) {
//        //since there is a base username and you look up by username it never creates a new user unless the db is already empty
//        String username = "user@mail.com";
//        User user;
//        try {
//            user = userService.getUserByUsername(username);
//        } catch (ResponseStatusException e) {
//            user = new User();
//            user.setUsername(username);
//            user.setPassword("#@!user-strong-password!@#");
//            user.setUserRole(role);
//            userService.createUser(user);
//        }
//        LoginRequest request = new LoginRequest(user.getUsername(), user.getPassword());
//        String token = authService.login(request);
//        ((JavascriptExecutor) driver).executeScript(
//                "window.localStorage.setItem('REVAISSUE_TOKEN', arguments[0]);",
//                token
//        );
//    }

    public void authenticateUser(UserRole role) {
        String username = switch (role) {
            case ADMIN -> "admin";
            case TESTER -> "tester";
            case DEVELOPER -> "dev";
        };

        User user = userService.getUserByUsername(username);

        LoginRequest request = new LoginRequest(user.getUsername(), user.getPassword());
        String token = authService.login(request);

        ((JavascriptExecutor) driver).executeScript(
                "window.localStorage.setItem('REVAISSUE_TOKEN', arguments[0]);",
                token
        );
    }
}
