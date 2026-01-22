package com.example.RevaIssue.apiTesting.login;

import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdminLoginAPITest {

    @Autowired
    private UserRepository userRepository;


    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/auth";
        RestAssured.port = 8080;
    }


    @BeforeEach
    public void seedData() {
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("admin");
        user.setPassword("password");
        user.setUserRole(UserRole.ADMIN);
        userRepository.save(user);
    }

    @Test
    public void adminLoginPositiveTest(){
        User credentials = new User();
        credentials.setUsername("admin");
        credentials.setPassword("password");

        given()
            .contentType(ContentType.JSON)
            .body(credentials)
        .when()
            .post("login")
        .then()
            .statusCode(200);
    }

    @Test
    public void adminLoginWrongPasswordTest(){
        User credentials = new User();
        credentials.setUsername("admin");
        credentials.setPassword("wrong_password");

        given()
            .contentType(ContentType.JSON)
            .body(credentials)
        .when()
            .post("login")
        .then()
            .statusCode(401); // unauthorized
    }

    /**
     * This could be throwing a 403 or 401 but 404 makes sense as well
     */
    @Test
    public void adminLoginUserNotFoundTest(){
        User credentials = new User();
        credentials.setUsername("ghost_user");
        credentials.setPassword("password");

        given()
            .contentType(ContentType.JSON)
            .body(credentials)
        .when()
            .post("login")
        .then()
            .statusCode(404);
    }

    /**
     * Missing username should result in 400 Bad Request,
     * but the login endpoint currently returns 500.
     * */
    @Test
    @DisplayName("FAILED: admin login throws 500 for missing username (should be 400 Bad Request)")
    public void adminLoginMissingUsernameTest(){
        System.out.println(
                "============================================================\n" +
                "Login endpoint does not validate request input.\n" +
                "Missing username causes 500 instead of 400 Bad Request.\n" +
                "============================================================"
        );

        User credentials = new User();
        credentials.setPassword("password");

        given()
            .contentType(ContentType.JSON)
            .body(credentials)
        .when()
            .post("login")
        .then()
            .statusCode(400);
    }

}