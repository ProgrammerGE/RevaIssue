package com.example.RevaIssue.repository;

import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.RevaIssue.entity.Users_Projects;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Users_ProjectsRepository extends JpaRepository<Users_Projects, Integer> {
    List<Users_Projects> findByUser(User user);
    List<Users_Projects> findByProject(Project project);
}
