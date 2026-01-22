package com.example.RevaIssue.apiTesting.register;

import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.service.UserService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RegisterAPITest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/auth";
    }

    @BeforeEach
    public void seedData() {
        userRepository.deleteAll(); // Clean state

        User existing = new User();
        existing.setUsername("existingUser");
        existing.setPassword("password123");
        existing.setUserRole(UserRole.DEVELOPER);

        userService.createUser(existing);
    }

    @AfterEach
    public void cleanData(){
        // tests use a separate database file that's stored in ram, so this does not
        // clear the database every time it's run.
        userRepository.deleteAll(); // Clean state
    }
    
    @Test
    public void userRegisterSuccessful() {
        // controller expects a RegisterRequest object, so we use a map
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

    // test currently fails because a 500 error is thrown due to the Unique
    // constraint being violated in SQL.
    @Test
    @DisplayName("Should return 409 when registering with an existing username")
    public void registerFailDuplicateUsername() {
        Map<String, String> request = new HashMap<>();
        request.put("username", "existingUser");
        request.put("password", "password123");
        request.put("role", "DEVELOPER");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/register")
                .then()
                .statusCode(409); // Conflict
    }

    /**
     * <p>
     * java.lang.NullPointerException: Cannot invoke "String.toLowerCase()"
     * because the return value of "com.example.RevaIssue.dto.RegisterRequest.username()"
     * is null
     * <br>
     * I was expecting this to return a 500 database error, but it seems the AuthService method
     * also needs to check if the field is null before trying to modify it.
     * </p>
     */
    @Test
    @DisplayName("Should return 400 when request body is empty")
    public void registerFailEmptyBody() {
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post("/register")
                .then()
                .statusCode(400); // Likely will fail with 500 until fixed
    }

    /**
     * <p>
     * java.lang.NullPointerException: Cannot invoke "String.toLowerCase()"
     * because the return value of "com.example.RevaIssue.dto.RegisterRequest.username()"
     * is null
     * <br>
     * I was expecting this to return a 500 database error, but it seems the service method
     * also needs to check if the field is null before trying to modify it.
     * </p>
     */
    @Test
    @DisplayName("Should return 400 when fields are null")
    public void registerFailNullFields() {
        Map<String, String> request = new HashMap<>();
        request.put("username", null);
        request.put("password", "12345");
        request.put("role", null);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/register")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Should return 400 when role is invalid")
    public void registerFailInvalidRole() {
        Map<String, String> request = new HashMap<>();
        request.put("username", "new_user");
        request.put("password", "password");
        request.put("role", "NOT_A_REAL_ROLE");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/register")
                .then()
                .statusCode(400);
    }
}
