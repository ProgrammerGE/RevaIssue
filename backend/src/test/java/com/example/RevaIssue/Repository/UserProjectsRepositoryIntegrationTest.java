package com.example.RevaIssue.Repository;


import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.entity.User_Projects;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.repository.User_ProjectsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class UserProjectsRepositoryIntegrationTest {
    private final User_ProjectsRepository upRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    private Project project;
    private User user;
    private User_Projects up;

    @Autowired
    public UserProjectsRepositoryIntegrationTest(ProjectRepository projectRepository,
                                                 UserRepository userRepository,
                                                 User_ProjectsRepository upRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.upRepository = upRepository;
    }

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setProjectName("example project");
        project.setProjectDescription("projectDescription");
        project = projectRepository.save(project);

        user = new User();
        user.setUsername("john_doe");
        user.setPassword("password");
        user.setUserRole(UserRole.ADMIN);
        user = userRepository.save(user);

        up = new User_Projects();
        up.setProject(project);
        up.setUser(user);
        up = upRepository.save(up);

    }

    @Test
    void findByUserPositive() {
        // try to retrieve a list of User_Projects by user
        List<User_Projects> result = upRepository.findByUser(user);

        // assertions
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("example project", result.getFirst().getProject().getProjectName());
    }

    @Test
    void findByUserNegative() {
        // Should return an empty list for a user with no associated projects

        // make a new user with no associated projects
        User bob = new User();
        bob.setUsername("bob");
        bob.setPassword("password");
        bob.setUserRole(UserRole.TESTER);
        bob = userRepository.save(bob);
        List<User_Projects> result = upRepository.findByUser(bob);

        // assertions
        assertTrue(result.isEmpty());
    }

    @Test
    void findUsersByProjectIdPositive() {
        // try to retrieve a list of Users by project id
        List<User> result = upRepository.findUsersByProjectId(project.getProjectID());

        // assertions
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("john_doe", result.getFirst().getUsername());

    }

    @Test
    void findUsersByProjectIdNegative() {
        // Should return empty list if pId does not exist or has no users
        List<User> result = upRepository.findUsersByProjectId(project.getProjectID()+9999);
        // assertions
        assertTrue(result.isEmpty());
    }

    @Test
    void deleteByUsernameAndProjectIdPositive() {
        // ensures we have a record to delete
        assertEquals("john_doe", upRepository.findByUser(user).getFirst().getUser().getUsername());
        // Should successfully remove the record
        upRepository.deleteByUsernameAndProjectId("john_doe", project.getProjectID());
        // query the database and assert
        assertTrue(upRepository.findAll().isEmpty());
    }

    @Test
    void deleteByUsernameAndProjectIdNegative() {
        // Should verify no records are removed if username/pId don't match
        assertFalse(upRepository.findAll().isEmpty());

        // try to delete up where user & project both don't exist
        int r1 = upRepository.deleteByUsernameAndProjectId("Eric_Suminski", project.getProjectID() + 9999);

        // try to delete up where user exists but project doesn't
        int r2 = upRepository.deleteByUsernameAndProjectId("john_doe", project.getProjectID() + 9999);
        // try to delete up where project exists but user doesn't
        int r3 = upRepository.deleteByUsernameAndProjectId("Eric_Suminski", project.getProjectID());
        // try to delete up where user and project exist but aren't associated
        User bob = new User();
        bob.setUsername("david_bass");
        bob.setPassword("fishin");
        bob.setUserRole(UserRole.DEVELOPER);
        bob = userRepository.save(bob);
        int r4 = upRepository.deleteByUsernameAndProjectId("david_bass",  project.getProjectID());

        // assertions
        assertEquals(0, r1, "record was changed where user & project both don't exist");
        assertEquals(0, r2, "record was changed where user exists but project doesn't exist");
        assertEquals(0, r3, "record was changed where project exists but user doesn't exist");
        assertEquals(0, r4, "record was changed where user and project exists but aren't related");
    }


}
