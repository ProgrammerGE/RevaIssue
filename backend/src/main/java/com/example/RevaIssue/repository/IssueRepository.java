package com.example.RevaIssue.repository;

import com.example.RevaIssue.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.RevaIssue.entity.Issue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByProject(Project project);
    List<Issue> findByProjectProjectID(Long projectId);
}