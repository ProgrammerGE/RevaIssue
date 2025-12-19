package com.example.RevaIssue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.RevaIssue.entity.Users_Projects;
import org.springframework.stereotype.Repository;

@Repository
public interface Users_ProjectsRepository extends JpaRepository<Users_Projects, Integer> {
}
