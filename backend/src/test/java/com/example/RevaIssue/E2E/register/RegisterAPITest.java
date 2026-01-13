package com.example.RevaIssue.E2E.register;

import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RegisterAPITest {

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @BeforeEach
    public void registerSetup(){
        RestAssured.basePath = "/register";
    }

    @Test
    public void userRegisterSuccessful(){
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setUserRole(UserRole.ADMIN);
        given().contentType(ContentType.JSON)
                .body(user)
                .header("Authorization", "token")
                .when()
                .post("/auth/register")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }
}
