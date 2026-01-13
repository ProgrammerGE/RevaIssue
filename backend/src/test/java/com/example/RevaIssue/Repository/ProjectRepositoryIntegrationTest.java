package com.example.RevaIssue.Repository;

import com.example.RevaIssue.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class ProjectRepositoryIntegrationTest {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectRepositoryIntegrationTest(ProjectRepository projectRepository) { this.projectRepository = projectRepository; }

    @Test
    void findByKeywordPositiveTest() {

    }

    @Test
    void findByKeywordNegativeTest() {

    }
}
