package com.example.RevaIssue.Repository;

import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.helper.Comment;
import com.example.RevaIssue.repository.CommentRepository;
import com.example.RevaIssue.repository.IssueRepository;
import com.example.RevaIssue.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class CommentRepositoryIntegrationTest {
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;

    private Comment comment1;
    private Comment comment2;
    private Comment comment3;
    private Issue issue;
    private Project project;

    @Autowired
    public CommentRepositoryIntegrationTest(CommentRepository commentRepository,
                                            IssueRepository issueRepository,
                                            ProjectRepository projectRepository) {
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
    }

    @BeforeEach
    void setup() {
        project = new Project();
        project.setProjectName("Jira Fun Club");
        project.setProjectDescription("A cozy place to share all of our story points");
        project = projectRepository.save(project);

        issue = new Issue();
        issue.setName("Screen broke");
        issue.setDescription("I dropped my work laptop. Sorry!");
        issue.setProject(project);
        issue.setSeverity(1);
        issue.setPriority(3);
        issue.setStatus("open"); // may be redundant
        issue = issueRepository.save(issue);

        comment1 = new Comment();
        comment1.setText("I could probably fix this in two or three story points, I'll LYK");
        comment1.setTimeLogged(LocalDateTime.now());
        comment1.setIssue(issue);
        comment1 = commentRepository.save(comment1);

        comment2 = new Comment();
        comment2.setText("Awesome! Thanks!");
        comment2.setTimeLogged(LocalDateTime.now().plusMinutes(10));
        comment2.setIssue(issue);
        comment2 = commentRepository.save(comment2);

        comment3 = new Comment();
        comment3.setText("Yeaup. No problem.");
        comment3.setTimeLogged(LocalDateTime.now().plusHours(18));
        comment3.setIssue(issue);
        comment3 = commentRepository.save(comment3);
    }

    /**
     * The repository method this tests seems to be meant to return
     * a list of comments, ordered by the time logged.
     */
    @Test
    void findByIssueOrderedPositiveTest() {
        // get ordered comments by issue id
        List<Comment> result = commentRepository.findByIssue_IssueIDOrderByTimeLoggedAsc((long)issue.getIssueID());

        // assertions
        // verify there are three comments
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());

        // verify they are in the correct order (ascending, so oldest first)
        assertTrue(result.get(0).getTimeLogged().isBefore(result.get(1).getTimeLogged()),
                "The first comment should be older than the second");
        assertTrue(result.get(1).getTimeLogged().isBefore(result.get(2).getTimeLogged()),
                "The second comment should be older than the third");

        // verify content
        assertEquals("I could probably fix this in two or three story points, I'll LYK", result.getFirst().getText());
    }

    @Test
    void findByIssueOrderedNegativeTest() {

        // attempt to get comments for a non-existent ID
        List<Comment> result = commentRepository.findByIssue_IssueIDOrderByTimeLoggedAsc(9999L);

        // assertions
        assertNotNull(result, "The result should be an empty list, not null");
        assertTrue(result.isEmpty(), "The result list should be empty for a non-existent issue ID");
        assertEquals(0, result.size(), "The size of the result should be zero");
    }
}
