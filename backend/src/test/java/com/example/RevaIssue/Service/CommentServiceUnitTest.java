package com.example.RevaIssue.Service;

import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.helper.Comment;
import com.example.RevaIssue.service.CommentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class CommentServiceUnitTest {

    @Mock
    private CommentService commentService;

    @Test
    void addCommentTest(){
        Project mockProject = new Project();
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");
        mockProject.setProjectID(1);

        Issue mockIssue = new Issue();
        mockIssue.setIssueID(1);
        mockIssue.setName("Mock Issue");
        mockIssue.setDescription("Mock Description");
        mockIssue.setStatus("Open");
        mockIssue.setSeverity(1);
        mockIssue.setPriority(1);
        mockIssue.setDateCreated(LocalDateTime.now());
        mockIssue.setProject(mockProject);

        Comment mockComment = new Comment();
        mockComment.setComment_id((long)1);
        mockComment.setText("New Comment");
        mockComment.setIssue(mockIssue);
        mockComment.setTimeLogged(LocalDateTime.now());

        Comment newComment = commentService.addComment((long)1, "New Comment");
        assertNotNull(newComment);
        assertEquals(mockComment, newComment);
    }

    @Test
    void getCommentsByIssueTest(){

        Project mockProject = new Project();
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");
        mockProject.setProjectID(1);

        Issue mockIssue = new Issue();
        mockIssue.setIssueID(1);
        mockIssue.setName("Mock Issue");
        mockIssue.setDescription("Mock Description");
        mockIssue.setStatus("Open");
        mockIssue.setSeverity(1);
        mockIssue.setPriority(1);
        mockIssue.setDateCreated(LocalDateTime.now());
        mockIssue.setProject(mockProject);

        Comment mockComment = new Comment();
        mockComment.setComment_id((long)1);
        mockComment.setText("New Comment");
        mockComment.setIssue(mockIssue);
        mockComment.setTimeLogged(LocalDateTime.now());

        List<Comment> mockCommentList = new ArrayList<>();
        mockCommentList.add(mockComment);

        when(commentService.addComment((long)1, "New Comment")).thenReturn(mockComment);

        List<Comment> commentList = commentService.getCommentsByIssue((long)1);
        assertNotNull(commentList);
        assertEquals(mockCommentList.getFirst(), commentList.getFirst());

    }
}
