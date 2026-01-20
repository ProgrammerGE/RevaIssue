package com.example.RevaIssue.apiTesting.authorized;

import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.service.UserService;
import com.example.RevaIssue.util.JwtUtility;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

/**
 * Had to use the seed data from the RegisterAPITest file
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthUserInfoAPITest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    JwtUtility jwtUtility;

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @BeforeEach
    public void auditLogSetup(){
        RestAssured.basePath = "/auth";

        //Following Chris's example from RegisterAPITest
        userRepository.deleteAll();

        User existing = new User();
        existing.setUsername("newUser");
        existing.setPassword("password123");
        existing.setUserRole(UserRole.DEVELOPER);
        jwtUtility.generateAccessToken(existing.getUsername(), existing.getUserRole());
        userService.createUser(existing);
    }

    @AfterEach
    public void cleanData(){ //Following Chris's example from RegisterAPITest
        userRepository.deleteAll();
    }

    @Test
    public void getUserInfoPositiveTest(){
        String token = "Bearer " + jwtUtility.generateAccessToken("newUser", UserRole.DEVELOPER);
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get("/userInfo")
                .then()
                .statusCode(200)
                .body("username", notNullValue())
                .body("role", notNullValue());
    }

    @Test
    public void getUserInfoNegativeTest(){
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "non-existing token")
                .when()
                .get("/userInfo")
                .then()
                .statusCode(500);
    }

    @Test
    public void validateUserTokenPositiveTest(){
        String token = "Bearer " + jwtUtility.generateAccessToken("newUser", UserRole.DEVELOPER);
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get("/user")
                .then()
                .statusCode(204);//NO_CONTENT
    }

    @Test
    public void validateUserTokenNegativeTest(){
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "random token")
                .when()
                .get("/user")
                .then()
                .statusCode(401); //UNAUTHORIZED
    }
}
