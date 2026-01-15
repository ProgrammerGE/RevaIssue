package com.example.RevaIssue.apiTesting.issue;


import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IssueAPITest {

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @BeforeEach
    public void issueSetup(){
        RestAssured.basePath = "/${role}/project";
    }

    @Test
    public void createIssuePositiveTest(){
        Project newProject = new Project();
        newProject.setProjectID(1);
        newProject.setProjectName("Project Name");
        newProject.setProjectDescription("Description");

        Issue newIssue = new Issue();
        newIssue.setIssueID(1);
        newIssue.setName("New Issue");
        newIssue.setDescription("New Description");
        newIssue.setStatus("Open");
        newIssue.setSeverity(1);
        newIssue.setPriority(1);
        newIssue.setDateCreated(LocalDateTime.now());
        newIssue.setProject(newProject);

        given().pathParam("role", "tester")
                .pathParam("projectId", "1")
                .contentType(ContentType.JSON)
                .body(newIssue)
                .header("Authorization", "token goes here")
                .post("/{projectId}/issues")
                .then()
                .statusCode(200)
                .body("issue", notNullValue());
    }

    @Test
    public void updateIssueStatusOpenPositiveTest(){
        given()
                .pathParam("role", "tester")
                .pathParam("issueId", "1")
                .pathParam("action", "OPEN")
                .contentType(ContentType.JSON)
                .header("Authorization", "token goes here")
                .patch("/issues/{issueId}/{action}")
                .then()
                .statusCode(200)
                .body("issue", notNullValue());
    }

    @Test
    public void updateIssueStatusClosePositiveTest(){
        given()
                .pathParam("role", "tester")
                .pathParam("issueId", "1")
                .pathParam("action", "CLOSED")
                .contentType(ContentType.JSON)
                .header("Authorization", "token goes here")
                .patch("/issues/{issueId}/{action}")
                .then()
                .statusCode(200)
                .body("issue", notNullValue());
    }

    @Test
    public void updateIssueStatusInProgressPositiveTest(){
        given()
                .pathParam("role", "developer")
                .pathParam("issueId", "1")
                .pathParam("action", "IN_PROGRESS")
                .contentType(ContentType.JSON)
                .header("Authorization", "token goes here")
                .patch("/issues/{issueId}/{action}")
                .then()
                .statusCode(200)
                .body("issue", notNullValue());
    }

    @Test
    public void updateIssueStatusResolvedPositiveTest(){
        given()
                .pathParam("role", "developer")
                .pathParam("issueId", "1")
                .pathParam("action", "RESOLVED")
                .contentType(ContentType.JSON)
                .header("Authorization", "token goes here")
                .patch("/issues/{issueId}/{action}")
                .then()
                .statusCode(200)
                .body("issue", notNullValue());
    }

    @Test
    public void viewAllIssuesPositiveTest(){
        given()
                .pathParam("role", "developer")
                .pathParam("project_id", "1")
                .contentType(ContentType.JSON)
                .header("Authorization", "token goes here")
                .get("/${project_id}/issues")
                .then()
                .statusCode(200)
                .body("issue_list", notNullValue());
    }
}
