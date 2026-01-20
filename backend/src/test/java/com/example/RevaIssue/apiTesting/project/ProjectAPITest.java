package com.example.RevaIssue.apiTesting.project;

import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.entity.User_Projects;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.repository.User_ProjectsRepository;
import com.example.RevaIssue.service.ProjectService;
import com.example.RevaIssue.service.UserService;
import com.example.RevaIssue.util.JwtUtility;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
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
        // The controller maps /admin, and methods add /projects.
        // Setting basePath to /admin makes the given() paths cleaner.
        RestAssured.basePath = "/admin";
    }

    @BeforeEach
    public void seedData() {
        user_ProjectsRepository.deleteAll();
        projectRepository.deleteAll();
        userRepository.deleteAll();

        // 1. Generate a valid Admin Token
        adminToken = "Bearer " + jwtUtility.generateAccessToken("adminUser", UserRole.ADMIN);

        // 2. Create a test user
        User user = new User();
        user.setUsername(TEST_USER);
        user.setPassword("password");
        user.setUserRole(UserRole.DEVELOPER);
        userRepository.save(user);

        // 3. Create a test project and capture the dynamic ID
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
                .body(is(notNullValue())) // Check that it exists
                .extract().asString().equals("true"); // Explicitly check string value
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
                .body(equalTo("true")); // Matches the raw text response "true"
    }
}
