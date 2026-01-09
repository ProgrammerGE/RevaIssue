package com.example.RevaIssue.service;

import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.helper.Comment;
import com.example.RevaIssue.repository.CommentRepository;
import com.example.RevaIssue.repository.IssueRepository;
import com.example.RevaIssue.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;

    public CommentService(CommentRepository commentRepository, IssueRepository issueRepository){
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
    }

    public Comment addComment(Long issueId, String text){
        Issue issue = issueRepository.getReferenceById(issueId);

        Comment comment = new Comment();
        comment.setText(text);
        comment.setTimeLogged(LocalDateTime.now());
        comment.setIssue(issue);

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByIssue(Long issueId){
        return commentRepository.findByIssue_IssueIDOrderByTimeLoggedAsc(issueId);
    }
}
