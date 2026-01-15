package com.example.RevaIssue.apiTesting.auditLog;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuditLogAPITest {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @BeforeEach
    public void auditLogSetup(){
        RestAssured.basePath = "/admin";
    }

    @Test
    public void getAllAuditLogsPositiveTest(){
        given()
                .contentType(ContentType.JSON)
                .get("/audits")
                .then()
                .statusCode(200)
                .body("auditlog_list", notNullValue());
    }
}
