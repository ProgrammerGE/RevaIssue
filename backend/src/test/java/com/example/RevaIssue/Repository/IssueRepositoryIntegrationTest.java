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

import java.time.LocalDateTime;
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

        List<Issue> issueList = issueRepository.findByProjectProjectID((long)project.getProjectID());

        // ensure the list has stuff innit
        assertTrue(issueList.contains(testIssue));

    }

    @Test
    void findByProjectIdNegativeTest() {
        List<Issue> issues;
        // Step 1: look for issues in non-existent project:
        issues = issueRepository.findByProjectProjectID(9999L);
        assertNotNull(issues);
        assertTrue(issues.isEmpty());
        // Step 2: look for issues in existing project without them
        issueRepository.deleteAll();
        issues = issueRepository.findByProjectProjectID((long)project.getProjectID());
        assertNotNull(issues);
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
        assertNotNull(issues);
        assertFalse(issues.contains(testIssue));
    }

    @Test
    void findTop5PositiveTest() {
        // Step 1: Create 6 issues with distinct timestamps
        List<Issue> issuesToSave = new ArrayList<>();
        LocalDateTime baseTime = LocalDateTime.now();

        for (int i = 0; i < 6; i++) {
            Issue issue = new Issue();
            issue.setName("Issue number " + (i + 1));
            issue.setDescription("Description " + (i + 1));
            issue.setProject(project);
            issue.setSeverity(1);
            issue.setPriority(1);
            issue.setStatus("open");

            // Manually setting dates to ensure "Issue 6" is the newest
            issue.setDateCreated(baseTime.plusMinutes(i));
            issuesToSave.add(issue);
        }

        issueRepository.saveAll(issuesToSave);

        // Step 2: Search for the top 5
        List<Issue> results = issueRepository.findTop5ByOrderByDateCreatedDesc();

        // Step 3: Assertions
        // Ensure we only got 5 out of the 6
        assertEquals(5, results.size(), "Didn't return 5 results");

        // Ensure they are ordered by dateCreated DESC (Newest first)
        // The first item should be the one we created last (Issue 6)
        assertEquals("Issue number 6", results.getFirst().getName());

        // Verify the descending order across the list
        for (int i = 0; i < results.size() - 1; i++) {
            LocalDateTime current = results.get(i).getDateCreated();
            LocalDateTime next = results.get(i + 1).getDateCreated();

            assertTrue(current.isAfter(next) || current.isEqual(next),
                    "Results are not in descending order at index " + i);
        }
    }

    @Test
    void findTop5NegativeTest() {
        // When the database is empty
        issueRepository.deleteAll();
        List<Issue> results = issueRepository.findTop5ByOrderByDateCreatedDesc();

        // Then it should return an empty list, NOT null
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void findByFilterPositiveTest() {
        // Create an issue that matches all 3 criteria
        Issue issue = new Issue();
        issue.setName("Match");
        issue.setStatus("closed");
        issue.setSeverity(2);
        issue.setPriority(1);
        issue.setProject(project);

        issueRepository.save(issue);

        // Search with the exact matching values
        List<Issue> results = issueRepository.findByFilter("closed", 2, 1);

        // Check that we found exactly 1 result and it's the right one
        assertEquals(1, results.size(), "Should have found exactly 1 matching issue");
        assertEquals("Match", results.getFirst().getName());
    }

    @Test
    void findByFilterNegativeTest() {
        // Create an issue that is ALMOST a match (Priority is wrong)
        Issue issue = new Issue();
        issue.setName("Non-Match");
        issue.setStatus("open");
        issue.setSeverity(2);
        issue.setPriority(3); // This is the difference
        issue.setProject(project);

        issueRepository.save(issue);

        // Search with values that don't match the issue
        List<Issue> results = issueRepository.findByFilter("open", 2, 1);

        // The list should be empty because priority 3 != 1
        assertTrue(results.isEmpty(), "Result list should be empty for partial matches");
    }
}
