package com.example.RevaIssue.Repository;


import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.repository.User_ProjectsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class UserProjectsRepositoryIntegrationTest {
    private final User_ProjectsRepository upRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserProjectsRepositoryIntegrationTest(ProjectRepository projectRepository, UserRepository userRepository, User_ProjectsRepository upRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.upRepository = upRepository;
    }

    @Test
    void findByUserPositive() {
        // Should return a list of User_Projects for a user who has projects
    }

    @Test
    void findByUserNegative() {
        // Should return an empty list for a user with no associated projects
    }

    @Test
    void findByProjectPositive() {
        // Should return a list of User_Projects for a project that has users
    }

    @Test
    void findByProjectNegative() {
        // Should return an empty list for a project with no associated users
    }

    @Test
    void findUsersByProjectIdPositive() {
        // Should return list of Users specifically linked to the given pId
    }

    @Test
    void findUsersByProjectIdNegative() {
        // Should return empty list if pId does not exist or has no users
    }

    @Test
    void deleteByUserAndProjectPositive() {
        // Should successfully remove the specific User_Project record
    }

    @Test
    void deleteByUserAndProjectNegative() {
        // Should not throw an error if the relationship doesn't exist
    }

    @Test
    void deleteByUsernameAndProjectIdPositive() {
        // Should successfully remove the record using the @Modifying query
    }

    @Test
    void deleteByUsernameAndProjectIdNegative() {
        // Should verify no records are removed if username/pId don't match
    }




}
