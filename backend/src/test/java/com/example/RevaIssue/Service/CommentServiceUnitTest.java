package com.example.RevaIssue.Service;

import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.helper.Comment;
import com.example.RevaIssue.repository.CommentRepository;
import com.example.RevaIssue.repository.IssueRepository;
import com.example.RevaIssue.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceUnitTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void addComment_PositiveTest() {
        // mock data
        Long issueId = 1L;
        String commentText = "New Comment";

        Issue mockIssue = new Issue();
        mockIssue.setIssueID(1);
        mockIssue.setName("Mock Issue");


        when(issueRepository.findById(issueId)).thenReturn(Optional.of(mockIssue));
        // save methods return input object
        when(commentRepository.save(any(Comment.class))).thenAnswer(i -> i.getArguments()[0]);

        // try to add a comment
        Comment result = commentService.addComment(issueId, commentText);

        // assertions
        assertNotNull(result);
        // verifying specific fields because timestamps generated inside the service
        // will not match mock object's timestamp.
        assertEquals(commentText, result.getText());
        assertEquals(issueId, result.getIssue().getIssueID());
        verify(issueRepository, times(1)).findById(issueId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void addComment_IssueNotFoundTest() {
        // mock data
        Long issueId = 1L;
        when(issueRepository.findById(issueId)).thenReturn(Optional.empty());

        // try to add a comment to an issue that doesn't exist
        Comment result = commentService.addComment(issueId, "Text");

        // assertions
        assertNull(result);
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void getCommentsByIssue_PositiveTest() {
        // mock data
        Long issueId = 1L;
        Comment comment1 = new Comment();
        comment1.setText("Comment 1");

        List<Comment> mockList = new ArrayList<>();
        mockList.add(comment1);

        when(commentRepository.findByIssue_IssueIDOrderByTimeLoggedAsc(issueId)).thenReturn(mockList);

        // try to get comments by issue id
        List<Comment> result = commentService.getCommentsByIssue(issueId);

        // assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Comment 1", result.getFirst().getText());
        verify(commentRepository, times(1)).findByIssue_IssueIDOrderByTimeLoggedAsc(issueId);
    }
}