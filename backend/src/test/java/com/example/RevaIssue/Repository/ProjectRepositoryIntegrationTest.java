package com.example.RevaIssue.Repository;

import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class ProjectRepositoryIntegrationTest {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectRepositoryIntegrationTest(ProjectRepository projectRepository) { this.projectRepository = projectRepository; }

    @Test
    void findByKeywordPositiveTest() {
        // Create a project that matches the keyword
        Project project = new Project();
        project.setProjectName("Super Awesome Manager");
        project.setProjectDescription("A Awesome management tool");
        projectRepository.save(project);

        // Search for a partial string present in the description
        String keyword = "management";
        List<Project> results = projectRepository.findByKeyword(keyword);

        // Verify the list is not empty and contains the correct project
        assertNotNull(results, "The returned list is null and should not be");
        assertFalse(results.isEmpty(), "The returned list is empty and should not be");
        assertTrue(results.stream().anyMatch(p -> p.getProjectName().equals("Super Awesome Manager")));
    }

    @Test
    void findByKeywordNegativeTest() {
        // Create a project that does NOT match the search criteria
        Project project = new Project();
        project.setProjectName("Almost as Awesome Manager");
        project.setProjectDescription("This one is pretty good too");
        projectRepository.save(project);

        // Search for a keyword that exists nowhere in any record
        String keyword = "Super";
        List<Project> results = projectRepository.findByKeyword(keyword);

        // Verify the list is empty, and not null
        assertNotNull(results, "The returned list is null and should not be");
        assertTrue(results.isEmpty(), "Results should be empty when no matches exist");
    }
}
