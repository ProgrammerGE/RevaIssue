package com.example.RevaIssue.apiTesting.common;

import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.helper.Comment;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

/**
 * This is for api testing on common http calls between projects and issues.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CommonAPITest {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @BeforeEach
    public void commonSetup(){
        RestAssured.basePath = "/common";
    }

    /**
     * =========================================
     *         Common Issue Tests
     * =========================================
     */
    @Test
    public void updateIssuesPositiveTest(){
        Project newProject = new Project();
        newProject.setProjectID(1);
        newProject.setProjectName("Project Name");
        newProject.setProjectDescription("Description");

        Issue updatedIssue = new Issue();
        updatedIssue.setIssueID(1);
        updatedIssue.setName("Updated Issue");
        updatedIssue.setDescription("Updated Description");
        updatedIssue.setStatus("Open");
        updatedIssue.setSeverity(2);
        updatedIssue.setPriority(3);
        updatedIssue.setDateCreated(LocalDateTime.now());
        updatedIssue.setProject(newProject);

        given()
                .pathParam("issueId", "1")
                .contentType(ContentType.JSON)
                .body(updatedIssue)
                .header("Authorization", "token goes here")
                .patch("/issues/{issueId}")
                .then()
                .statusCode(200)
                .body("issue", notNullValue());
    }

    @Test
    public void viewAllIssuesByFilterPositiveTest(){
        given()
                .pathParam("status", "Open")
                .pathParam("severity", "1")
                .pathParam("priority", "1")
                .contentType(ContentType.JSON)
                .get("/issues/filter/{status}/{severity}/{priority}")
                .then()
                .statusCode(200)
                .body("issue_list", notNullValue());
    }

    @Test
    public void getMostRecentIssuesPositiveTest(){
        given()
                .contentType(ContentType.JSON)
                .get("/issues/latest")
                .then()
                .statusCode(200)
                .body("issue_list", notNullValue());
    }

    @Test
    public void viewAllIssuesByKeywordPositiveTest(){
        given()
                .pathParam("keyword", "findMe")
                .contentType(ContentType.JSON)
                .get("/issues/search?keyword={keyword}")
                .then()
                .statusCode(200)
                .body("issue_list", notNullValue());
    }

    /**
     * =========================================
     *         Common Project Tests
     * =========================================
     */
    @Test
    public void viewAllProjectsPositiveTest(){
        given()
                .contentType(ContentType.JSON)
                .get("/projects")
                .then()
                .statusCode(200)
                .body("project_list", notNullValue());
    }

    @Test
    public void viewProjectPositiveTest(){
        given()
                .pathParam("projectId", "1")
                .contentType(ContentType.JSON)
                .get("/projects/{projectId}")
                .then()
                .statusCode(200)
                .body("project", notNullValue());
    }

    @Test
    public void viewAllProjectsByKeywordPositiveTest(){
        given()
                .pathParam("keyword", "findMe")
                .contentType(ContentType.JSON)
                .get("/projects/search?keyword={keyword}")
                .then()
                .statusCode(200)
                .body("project_list", notNullValue());
    }

    /**
     * =========================================
     *         Common User Tests
     * =========================================
     */

    @Test
    public void fetchUsersPositiveTest(){
        given()
                .pathParam("pId", "1")
                .contentType(ContentType.JSON)
                .header("Authorization", "token goes here")
                .get("/projects/${pId}/users")
                .then()
                .statusCode(200)
                .body("user", notNullValue());
    }

    /**
     * =========================================
     *         Common Comment Tests
     * =========================================
     */

    @Test
    public void addCommentPositiveTest(){
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

        Comment comment = new Comment();
        comment.setComment_id((long)1);
        comment.setText("Comment text");
        comment.setTimeLogged(LocalDateTime.now());
        comment.setIssue(newIssue);

        given()
                .pathParam("issueId", "1")
                .contentType(ContentType.JSON)
                .body(comment)
                .header("Authorization", "token goes here")
                .post("/issues/{issueId}/comments")
                .then()
                .statusCode(200)
                .body("comment", notNullValue());
    }

    @Test
    public void loadCommentsPositiveTest(){
        given()
                .pathParam("issueId", "1")
                .contentType(ContentType.JSON)
                .get("/issues/{issueId}/comments")
                .then()
                .statusCode(200)
                .body("comment_list", notNullValue());
    }
}
