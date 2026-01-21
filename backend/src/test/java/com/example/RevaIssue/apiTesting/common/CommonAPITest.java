package com.example.RevaIssue.apiTesting.common;

import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.helper.Comment;
import com.example.RevaIssue.repository.*;
import com.example.RevaIssue.service.IssueService;
import com.example.RevaIssue.service.ProjectService;
import com.example.RevaIssue.service.UserService;
import com.example.RevaIssue.util.JwtUtility;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

/**
 * This is for api testing on common http calls between projects and issues.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CommonAPITest {
    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private IssueService issueService;
    @Autowired
    private User_ProjectsRepository user_projectsRepository;

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @BeforeEach
    public void commonSetup(){
        RestAssured.basePath = "/common";

        //Following Chris and Elijah's example from RegisterAPITest and IssueAPITest

        issueRepository.deleteAllInBatch();
        projectRepository.deleteAllInBatch();
        user_projectsRepository.deleteAllInBatch();

        Project newProject = new Project();
        newProject.setProjectName("Project Name");
        newProject.setProjectDescription("Description");
        projectService.createProject(newProject);

        Issue newIssue = new Issue();
        newIssue.setName("New Issue");
        newIssue.setDescription("New Description");
        newIssue.setStatus("Open");
        newIssue.setSeverity(1);
        newIssue.setPriority(1);
        newIssue.setDateCreated(LocalDateTime.now());
        newIssue.setProject(newProject);
        issueService.createIssue(newIssue);
    }

    @AfterEach
    public void cleanData(){ //Following Chris's example from RegisterAPITest
        projectRepository.deleteAllInBatch();
        issueRepository.deleteAllInBatch();
    }

    /**
     * =========================================
     *         Common Issue Tests
     * =========================================
     */
    @Test
    public void updateIssuesPositiveTest(){
        String token = "Bearer " + jwtUtility.generateAccessToken("newUser", UserRole.DEVELOPER);
        Project newProject = new Project();
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
                .header("Authorization", token)
                .when()
                .patch("/issues/{issueId}")
                .then()
                .statusCode(200)
                .body("name", notNullValue())
                .body("description", notNullValue())
                .body("severity", notNullValue())
                .body("priority", notNullValue());
    }

    @Test
    public void viewAllIssuesByFilterPositiveTest(){
        given()
                .pathParam("status", "Open")
                .pathParam("severity", "1")
                .pathParam("priority", "1")
                .contentType(ContentType.JSON)
                .when()
                .get("/issues/filter/{status}/{severity}/{priority}")
                .then()
                .statusCode(200)
                .body("issue_list", notNullValue());
    }

    @Test
    public void getMostRecentIssuesPositiveTest(){
        given()
                .contentType(ContentType.JSON)
                .when()
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
                .when()
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
                .when()
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
                .when()
                .get("/projects/{projectId}")
                .then()
                .statusCode(200)
                .body("projectName", notNullValue())
                .body("projectDescription", notNullValue());
    }

    @Test
    public void viewAllProjectsByKeywordPositiveTest(){
        given()
                .pathParam("keyword", "findMe")
                .contentType(ContentType.JSON)
                .when()
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
                .pathParam("id", "1")
                .contentType(ContentType.JSON)
                .header("Authorization", "token goes here")
                .when()
                .get("/projects/{id}/users")
                .then()
                .statusCode(200)
                .body("username", notNullValue())
                .body("role", notNullValue());
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
                .when()
                .post("/issues/{issueId}/comments")
                .then()
                .statusCode(200)
                .body("comment_id", notNullValue())
                .body("text", notNullValue())
                .body("timeLogged", notNullValue());
    }

    @Test
    public void loadCommentsPositiveTest(){
        given()
                .pathParam("issueId", "1")
                .contentType(ContentType.JSON)
                .when()
                .get("/issues/{issueId}/comments")
                .then()
                .statusCode(200)
                .body("comment_list", notNullValue());
    }
}
