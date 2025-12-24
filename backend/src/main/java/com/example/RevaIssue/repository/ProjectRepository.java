package com.example.RevaIssue.repository;
import com.example.RevaIssue.entity.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    // TODO : add a method that accepts a list of ids, queries the database, and returns a list of projects
}
