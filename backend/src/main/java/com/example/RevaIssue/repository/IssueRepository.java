package com.example.RevaIssue.repository;

import com.example.RevaIssue.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.RevaIssue.entity.Issue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByProject(Project project);
    List<Issue> findByProjectProjectID(Long projectId);
    @Query("FROM issues WHERE issue_description LIKE %:keyword% AND issue_name LIKE %:keyword%")
    List<Issue> findByKeyword(@Param("keyword") String keyword);
}