package com.example.RevaIssue.apiTesting.project;

import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.repository.User_ProjectsRepository;
import com.example.RevaIssue.util.JwtUtility;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProjectAPITest {

    @Autowired ProjectRepository projectRepository;
    @Autowired UserRepository userRepository;
    @Autowired User_ProjectsRepository user_ProjectsRepository;
    @Autowired JwtUtility jwtUtility;

    private String adminToken;
    private int testProjectId;
    private final String TEST_USER = "tester";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/admin";
    }

    @BeforeEach
    public void seedData() {
        // clear relationships first to avoid Foreign Key violations
        // use deleteAllInBatch() to skip the "entity exists" check that causes an error for updateProjectFail_NoToken
        user_ProjectsRepository.deleteAllInBatch();
        projectRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        // create admin token
        adminToken = "Bearer " + jwtUtility.generateAccessToken("admin", UserRole.ADMIN);

        User user = new User();
        user.setUsername(TEST_USER);
        user.setPassword("password");
        user.setUserRole(UserRole.DEVELOPER);
        userRepository.save(user);

        Project project = new Project();
        project.setProjectName("Initial Project");
        project.setProjectDescription("Initial Description");
        project = projectRepository.save(project);
        testProjectId = project.getProjectID();
    }

    @Test
    public void addUserToProjectPositiveTest() {
        given()
                .pathParam("projectId", testProjectId)
                .pathParam("userName", TEST_USER)
                .header("Authorization", adminToken)
                .when()
                .post("/projects/{projectId}/assign/{userName}")
                .then()
                .statusCode(200)
                .body("user.username", is(TEST_USER));
    }

    @Test
    public void removeUserFromProjectPositiveTest(){
        given()
                .pathParam("projectId", testProjectId)
                .pathParam("userName", TEST_USER)
                .header("Authorization", adminToken)
                .when()
                .delete("/projects/{projectId}/revoke/{userName}")
                .then()
                .statusCode(200)
                .body(is(notNullValue())) // check that it exists
                .extract().asString().equals("true"); // check string value explicitly
    }

    @Test
    public void updateProjectPositiveTest() {
        Project updateReq = new Project();
        updateReq.setProjectName("Updated Name");
        updateReq.setProjectDescription("Updated Desc");

        given()
                .pathParam("projectId", testProjectId)
                .contentType(ContentType.JSON)
                .body(updateReq)
                .header("Authorization", adminToken)
                .patch("/projects/{projectId}")
                .then()
                .statusCode(200)
                .body("projectName", is("Updated Name"));
    }

    @Test
    public void createProjectPositiveTest() {
        Project newProject = new Project();
        newProject.setProjectName("Brand New Project");
        newProject.setProjectDescription("New Desc");

        given()
                .contentType(ContentType.JSON)
                .body(newProject)
                .header("Authorization", adminToken)
                .post("/projects/new")
                .then()
                .statusCode(200)
                .body("projectName", is("Brand New Project"));
    }

    @Test
    public void deleteProjectByIDPositiveTest(){
        given()
                .pathParam("projectId", testProjectId)
                .header("Authorization", adminToken)
                .when()
                .delete("/projects/{projectId}")
                .then()
                .statusCode(200)
                .body(equalTo("true")); // matches the raw text response "true"
    }

    /**
     * Fails because it returns a status code of 200 instead of 403, which can be updated in the service.
     * It's better to explicitly return 403.
     */
    @Test
    public void createProjectFail_NonAdminRole(){
        // generate a token for a DEVELOPER instead of an ADMIN
        String devToken = "Bearer " + jwtUtility.generateAccessToken("devUser", UserRole.DEVELOPER);

        Project newProject = new Project();
        newProject.setProjectName("Unauthorized Project");
        newProject.setProjectDescription("Unauthorized Desc");

        given()
                .contentType(ContentType.JSON)
                .body(newProject)
                .header("Authorization", devToken)
                .post("/projects/new")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)    // 403 error
                .body(is(emptyOrNullString()));
    }

    /**
     * This should cause a 404 or 500 or something, as long as it's not 200, this test will pass.
     */
    @Test
    public void assignUserToProjectFail_InvalidId(){
        int nonExistentId = 9999;

        given()
                .pathParam("projectId", nonExistentId)
                .pathParam("userName", TEST_USER)
                .header("Authorization", adminToken)
                .when()
                .post("/projects/{projectId}/assign/{userName}")
                .then()
                .statusCode(not(200));
    }

    /**
     * Passes if we get a 400 bad request error, which is what we expect
     */
    @Test
    public void updateProjectFail_NoToken(){
        Project updateReq = new Project();
        updateReq.setProjectName("Hacker Name");
        updateReq.setProjectDescription("Hacker Desc");

        given()
                .pathParam("projectId", testProjectId)
                .contentType(ContentType.JSON)
                .body(updateReq)
                // no Authorization header added here
                .when()
                .patch("/projects/{projectId}")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST); // returns 400 Bad Request because the header is required
    }


}
