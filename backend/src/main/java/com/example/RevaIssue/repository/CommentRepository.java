package com.example.RevaIssue.repository;

import com.example.RevaIssue.helper.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByIssue_IssueIDOrderByTimeLoggedAsc(Long issueId);
}
