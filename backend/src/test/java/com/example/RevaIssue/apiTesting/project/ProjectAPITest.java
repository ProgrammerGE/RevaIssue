package com.example.RevaIssue.apiTesting.project;

import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProjectAPITest {

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @BeforeEach
    public void projectSetup(){
        RestAssured.basePath = "/admin/projects";
    }

    @Test
    public void addUserToProjectPositiveTest(){
        User credentials = new User();
        credentials.setUsername("tester");
        credentials.setPassword("password");
        given()
                .pathParam("projectId", "1")
                .pathParam("userName", "tester")
                .contentType(ContentType.JSON)
                .body(credentials)
                .header("Authorization", "token goes here")
                .when()
                .post("/{projectId}/assign/{userName}")
                .then()
                .statusCode(200)
                .body(is(true));
    }

    @Test
    public void removeUserFromProjectPositiveTest(){
        given()
                .pathParam("projectId", "1")
                .pathParam("userName", "tester")
                .contentType(ContentType.JSON)
                .header("Authorization", "token goes here")
                .when()
                .delete("/{projectId}/assign/{userName}")
                .then()
                .statusCode(200)
                .body(is(true));
    }

    @Test
    public void updateProjectPositiveTest(){
        Project updatedProject = new Project();
        updatedProject.setProjectID(1);
        updatedProject.setProjectName("Updated Project Name");
        updatedProject.setProjectDescription("Updated Description");
        given()
                .pathParam("projectId", "1")
                .contentType(ContentType.JSON)
                .body(updatedProject)
                .header("Authorization", "token goes here")
                .patch("/{projectId}")
                .then()
                .statusCode(200)
                .body("project", notNullValue());

    }

    @Test
    public void createProjectPositiveTest(){
        Project newProject = new Project();
        newProject.setProjectID(1);
        newProject.setProjectName("Project Name");
        newProject.setProjectDescription("Description");
        given()
                .contentType(ContentType.JSON)
                .body(newProject)
                .header("Authorization", "token goes here")
                .post("/new")
                .then()
                .statusCode(200)
                .body("project", notNullValue());
    }

    @Test
    public void deleteProjectByIDPositiveTest(){
        given()
                .pathParam("projectId", "1")
                .contentType(ContentType.JSON)
                .header("Authorization", "token goes here")
                .delete("/{projectId}")
                .then()
                .statusCode(200)
                .body("project", notNullValue());
    }
}
