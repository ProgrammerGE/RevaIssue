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
    @Query("SELECT i FROM Issue i WHERE i.description LIKE %:keyword% OR i.name LIKE %:keyword%")
    List<Issue> findByKeyword(@Param("keyword") String keyword);
    List<Issue> findTop5ByOrderByDateCreatedDesc();
    @Query("SELECT i FROM Issue i WHERE i.status = :status AND i.severity = :severity AND i.priority = :priority")
    List<Issue> findByFilter(@Param("status") String status, @Param("severity") int severity,
                             @Param("priority") int priority);
}