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
        Long issueId = 1L;
        String commentText = "New Comment";
        Issue mockIssue = new Issue();
        mockIssue.setIssueID(1);

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(mockIssue));
        when(commentRepository.save(any(Comment.class))).thenAnswer(i -> i.getArguments()[0]);

        Comment result = commentService.addComment(issueId, commentText);

        assertNotNull(result);
        // verifying specific fields because internal timestamps vary by milliseconds
        assertEquals(commentText, result.getText());
        assertEquals(issueId, result.getIssue().getIssueID());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void addComment_IssueNotFoundTest() {
        Long issueId = 1L;
        when(issueRepository.findById(issueId)).thenReturn(Optional.empty());

        Comment result = commentService.addComment(issueId, "Valid Text");

        assertNull(result);
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void addComment_TextTooLongTest() {
        Long issueId = 1L;
        // Creating a string that exceeds 2000 characters
        String longText = "a".repeat(2001);

        Comment result = commentService.addComment(issueId, longText);

        assertNull(result);
        // verify that findById was never even called because validation failed first
        verify(issueRepository, never()).findById(anyLong());
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void addComment_NullTextTest() {
        Comment result = commentService.addComment(1L, null);

        assertNull(result);
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void getCommentsByIssue_PositiveTest() {
        Long issueId = 1L;
        List<Comment> mockList = List.of(new Comment(), new Comment());

        when(commentRepository.findByIssue_IssueIDOrderByTimeLoggedAsc(issueId)).thenReturn(mockList);

        List<Comment> result = commentService.getCommentsByIssue(issueId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(commentRepository, times(1)).findByIssue_IssueIDOrderByTimeLoggedAsc(issueId);
    }

    @Test
    void getCommentsByIssue_EmptyTest() {
        Long issueId = 1L;
        when(commentRepository.findByIssue_IssueIDOrderByTimeLoggedAsc(issueId)).thenReturn(new ArrayList<>());

        List<Comment> result = commentService.getCommentsByIssue(issueId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(commentRepository, times(1)).findByIssue_IssueIDOrderByTimeLoggedAsc(issueId);
    }
}