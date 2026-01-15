package com.example.RevaIssue.apiTesting.authorized;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthUserInfoAPITest {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @BeforeEach
    public void auditLogSetup(){
        RestAssured.basePath = "/auth";
    }

    @Test
    public void getUserInfoPositiveTest(){
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "token goes here")
                .get("/userInfo")
                .then()
                .statusCode(200)
                .body("userInfo", notNullValue());
    }
}
