package com.example.RevaIssue.Repository;

import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.repository.IssueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IssueRepositoryIntegrationTest {
    private IssueRepository issueRepository;

    @Autowired
    public  IssueRepositoryIntegrationTest(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Test
    void findByProjectIdPositiveTest() {
        Issue issue = new Issue();
        issue.setName("Test issue");
        issue.setDescription("Test issue description");
        issue.setSeverity(1);
        issue.setPriority(1);
    }
}
