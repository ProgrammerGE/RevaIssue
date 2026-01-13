package com.example.RevaIssue.Repository;

import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.repository.IssueRepository;
import com.example.RevaIssue.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IssueRepositoryIntegrationTest {
    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;

    private Project project;
    private Issue testIssue;

    @Autowired
    public  IssueRepositoryIntegrationTest(IssueRepository issueRepository,  ProjectRepository projectRepository) {
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
    }

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setProjectName("projectName");
        project.setProjectDescription("projectDescription");
        project = projectRepository.save(project);

        testIssue = new Issue();
        testIssue.setName("My screen broke");
        testIssue.setDescription("I dropped the laptop. Sorry!");
        testIssue.setProject(project);
        testIssue.setSeverity(1);
        testIssue.setPriority(3);
        testIssue.setStatus("open"); // may be redundant
        issueRepository.save(testIssue);

    }

    @Test
    void findByProjectIdPositiveTest() {
        // initialize the issue to be used
//        Issue issue = new Issue();
//        issue.setName("Test issue");
//        issue.setDescription("Test issue description");
//        issue.setProject(project);
//        issue.setSeverity(1);
//        issue.setPriority(1);
//        issue.setStatus("open"); // may be redundant
//        issueRepository.save(issue);

        List<Issue> issueList = issueRepository.findByProjectProjectID((long)project.getProjectID());

        // ensure the list has stuff innit
        assertTrue(issueList.contains(testIssue));

    }

    @Test
    void findByProjectIdNegativeTest() {
        List<Issue> issues;
        // Step 1: look for issues in non-existent project:
        issues = issueRepository.findByProjectProjectID((long)project.getProjectID() + 9999);
        assertTrue(issues.isEmpty());
        // Step 2: look for issues in existing project without them
        issueRepository.deleteAll();
        issues = issueRepository.findByProjectProjectID((long)project.getProjectID());
        assertTrue(issues.isEmpty());
    }

    @Test
    void findByKeyWordPositiveTest() {
        // look for issues with the keyword "laptop"
        List<Issue> issues = issueRepository.findByKeyword("laptop");
        assertTrue(issues.contains(testIssue));
    }

    @Test
    void findByKeyWordNegativeTest() {
        List<Issue> issues = issueRepository.findByKeyword("super-mario");
        assertFalse(issues.contains(testIssue));
    }
}
