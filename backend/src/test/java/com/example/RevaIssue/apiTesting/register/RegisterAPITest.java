package com.example.RevaIssue.apiTesting.register;

import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RegisterAPITest {

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/auth";
    }
    
    @Test
    public void userRegisterSuccessful() {
        // Use a Map or a dedicated RegisterRequest object to match the Controller
        Map<String, String> request = new HashMap<>();
        request.put("username", "new_dev_user");
        request.put("password", "SecurePass123");
        request.put("role", "DEVELOPER");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/register") // This maps to /auth/register
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }
}
