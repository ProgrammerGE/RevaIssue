package com.example.RevaIssue.E2E.popups;

import com.example.RevaIssue.entity.Project;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

/**
 * This test file will test the functionality of the popup
 * component for creating a project.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CreateProjectUnitTest {

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @BeforeEach
    public void hubpageSetup(){
        RestAssured.basePath = "/admin";
        //User credentials = new User();
        //credentials.setUsername("admin");
        //credentials.setPassword("admin");
    }

    @Test
    public void createProject(){
        Project newProject = new Project();
        newProject.setProjectName("New Project");
        newProject.setProjectDescription("New Description");
        given().contentType(ContentType.JSON)
                .body(newProject)
                .header("Authorization", "token goes here")
                .when()
                .post("/projects/new")
                .then()
                .statusCode(200)
                .body("project", notNullValue());
    }
}
