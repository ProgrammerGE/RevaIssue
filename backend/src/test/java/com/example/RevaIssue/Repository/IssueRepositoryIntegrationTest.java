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

    }

    @Test
    void findByProjectIdPositiveTest() {
        // initialize the issue to be used
        Issue issue = new Issue();
        issue.setName("Test issue");
        issue.setDescription("Test issue description");
        issue.setProject(project);
        issue.setSeverity(1);
        issue.setPriority(1);
        issue.setStatus("open"); // may be redundant
        issueRepository.save(issue);

        List<Issue> issueList = issueRepository.findByProjectProjectID((long)project.getProjectID());

        // ensure the list has stuff innit
        assertTrue(issueList.contains(issue));

    }

    @Test
    void findByProjectIdNegativeTest() {
        List<Issue> issues = new ArrayList<Issue>();
        // Step 1: look for issues in a project that doesn't exist:
        issues = issueRepository.findByProjectProjectID((long)9999);
        assertTrue(issues.isEmpty());
        // Step 2: look for issues in a project without them
        issues = issueRepository.findByProjectProjectID((long)project.getProjectID());
        assertTrue(issues.isEmpty());
    }

    @Test
    void findByKeyWordPositiveTest() {

    }
}
