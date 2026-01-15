package com.example.RevaIssue.apiTesting.login;

import com.example.RevaIssue.entity.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdminLoginAPITest {


    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }


    @BeforeEach
    public void loginSetup(){
        RestAssured.basePath = "/login";
    }

    @Test
    public void adminLoginPositiveTest(){

        User credentials = new User();
        credentials.setUsername("admin");
        credentials.setPassword("admin");
        given()
                .contentType(ContentType.JSON)

                .body(credentials)
                .header("Authorization", "token goes here")

                .when()
                .post("/admin")
                .then()
                .statusCode(200)
                .body("token", notNullValue());

    }

}