package com.example.RevaIssue.apiTesting.issue;


import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.repository.*;
import com.example.RevaIssue.util.JwtUtility;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IssueAPITest {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    User_ProjectsRepository user_ProjectsRepository;
    @Autowired
    JwtUtility jwtUtility;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private IssueRepository issueRepository;

    private String TesterToken;
    private String DeveloperToken;
    private int testProjectId;
    private int testIssueId;
    private Issue newIssue;


    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @BeforeEach
    public void seedData(){

        user_ProjectsRepository.deleteAllInBatch();
        issueRepository.deleteAllInBatch();
        commentRepository.deleteAllInBatch();
        projectRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        // create user tokens
        TesterToken = "Bearer " + jwtUtility.generateAccessToken("tester", UserRole.TESTER);
        DeveloperToken = "Bearer " + jwtUtility.generateAccessToken("dev", UserRole.DEVELOPER);

        User tester = new User();
        tester.setUsername("tester");
        tester.setPassword("tester");
        tester.setUserRole(UserRole.TESTER);
        userRepository.save(tester);


        User developer = new User();
        developer.setUsername("dev");
        developer.setPassword("dev");
        developer.setUserRole(UserRole.DEVELOPER);
        userRepository.save(developer);

        Project project = new Project();
        project.setProjectName("Initial Project");
        project.setProjectDescription("Initial Description");
        project = projectRepository.save(project);
        testProjectId = project.getProjectID();

        Issue issue = new Issue();
        issue.setProject(project);
        issue.setPriority(1);
        issue.setSeverity(1);
        issue.setName("issue name");
        issue.setDescription("desc");
        issue.setDateCreated(LocalDateTime.now());
        issue.setStatus("OPEN");
        issue = issueRepository.save(issue);
        testIssueId = issue.getIssueID();

        newIssue = issue;

    }
    @BeforeEach
    public void issueSetup(){
        RestAssured.basePath = "/{role}";
    }

    // =========================
    // Create Issue Tests
    // =========================
    @Test
    public void createIssuePositiveTest() {
        given()
            .pathParam("role", "tester")
            .pathParam("project_id", testProjectId)
            .header("Authorization", TesterToken)
            .contentType(ContentType.JSON)
            .body(newIssue)
        .when()
            .post("/project/{project_id}/issues")
        .then()
            .statusCode(200)
            .body("issueID", notNullValue())
            .body("name", equalTo("issue name"));
    }
    // FAILED TEST:
    // Returns 200 rather than 403 testerController doesn't check the user role. It's only blocked in the front end
    @Test
    @DisplayName("FAILED: testerController doesn't check the user role")
    public void createIssueNegativeTest() {
        given()
            .pathParam("role", "tester")
            .pathParam("project_id", testProjectId)
            .header("Authorization", DeveloperToken)
            .contentType(ContentType.JSON)
            .body(newIssue)
        .when()
            .post("/project/{project_id}/issues")
        .then()
            .statusCode(403);
    }

    // =========================
    // Reopen Issue Tests
    // =========================
    @Test
    public void openPositiveTest(){
        given()
            .pathParam("role", "tester")
            .pathParam("issueId", testIssueId)
            .contentType(ContentType.JSON)
            .header("Authorization", TesterToken)
        .when()
            .patch("/project/issues/{issueId}/open")
        .then()
            .statusCode(200)

            .body("status", equalTo("OPEN"));
    }
    @Test
    public void openNegativeTest(){
        given()
            .pathParam("role", "tester")
            .pathParam("issueId", testIssueId)
            .contentType(ContentType.JSON)
            .header("Authorization", DeveloperToken)
        .when()
            .patch("/project/issues/{issueId}/open")
        .then()
            .statusCode(403);
    }

    // =========================
    // Close Issue Tests
    // =========================
    @Test
    public void closePositiveTest(){
        given()
            .pathParam("role", "tester")
            .pathParam("issueId", testIssueId)
            .contentType(ContentType.JSON)
            .header("Authorization", TesterToken)
        .when()
            .patch("/project/issues/{issueId}/close")
        .then()
            .statusCode(200)
            .body("status", equalTo("CLOSED"));
    }
    @Test
    public void closeIssueNegativeTest() {
        given()
            .pathParam("role", "tester")
            .pathParam("issueId", testIssueId)
            .header("Authorization", DeveloperToken)
            .contentType(ContentType.JSON)
        .when()
            .patch("/project/issues/{issueId}/close")
        .then()
            .statusCode(403);
    }

    // =========================
    // In Progress Issue Tests
    // =========================
    @Test
    public void inProgressPositiveTest(){
        given()
            .pathParam("role", "developer")
            .pathParam("issueId", testIssueId)
            .contentType(ContentType.JSON)
            .header("Authorization", DeveloperToken)
        .when()
            .patch("/project/issues/{issueId}/in-progress")
        .then()
            .statusCode(200)
            .body("status", equalTo("IN_PROGRESS"));
    }
    @Test
    public void inProgressNegativeTest(){
        given()
            .pathParam("role", "developer")
            .pathParam("issueId", testIssueId)
            .contentType(ContentType.JSON)
            .header("Authorization", TesterToken)
        .when()
            .patch("/project/issues/{issueId}/in-progress")
        .then()
            .statusCode(403);
    }

    // =========================
    // Resolve Issue Tests
    // =========================
    @Test
    public void resolvedPositiveTest(){
        given()
            .pathParam("role", "developer")
            .pathParam("issueId", testIssueId)
            .contentType(ContentType.JSON)
            .header("Authorization", DeveloperToken)
        .when()
            .patch("/project/issues/{issueId}/resolve")
        .then()
            .statusCode(200)
            .body("status", equalTo("RESOLVED"));
    }
    @Test
    public void resolvedNegativeTest(){
        given()
            .pathParam("role", "developer")
            .pathParam("issueId", testIssueId)
            .contentType(ContentType.JSON)
            .header("Authorization", TesterToken)
        .when()
            .patch("/project/issues/{issueId}/resolve")
        .then()
            .statusCode(403);
    }

    // =========================
    // view issues
    // =========================
    @Test
    public void viewAllIssuesAsDeveloperTest(){
        given()
            .pathParam("role", "developer")
            .pathParam("project_id", testProjectId)
            .header("Authorization", DeveloperToken)
        .when()
            .get("/project/{project_id}/issues")
        .then()
            .statusCode(200)
            .body("issue_list", notNullValue());
    }
    @Test
    public void viewAllIssuesATesterTest(){
        given()
            .pathParam("role", "tester")
            .pathParam("project_id", testProjectId)
            .header("Authorization", TesterToken)
        .when()
            .get("/project/{project_id}/issues")
        .then()
            .statusCode(200)
            .body("issue_list", notNullValue());
    }
}
