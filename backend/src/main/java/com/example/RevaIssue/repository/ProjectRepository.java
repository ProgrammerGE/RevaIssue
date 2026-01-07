package com.example.RevaIssue.repository;
import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Query("SELECT i FROM Project i WHERE i.projectDescription LIKE %:keyword% OR i.projectName LIKE %:keyword%")
    List<Project> findByKeyword(@Param("keyword") String keyword);
}
