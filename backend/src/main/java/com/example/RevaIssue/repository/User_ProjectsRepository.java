package com.example.RevaIssue.repository;

import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.RevaIssue.entity.User_Projects;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface User_ProjectsRepository extends JpaRepository<User_Projects, Integer> {
    List<User_Projects> findByUser(User user);
    List<User_Projects> findByProject(Project project);

    @Query("SELECT up.user FROM User_Projects up WHERE up.project.projectID = :pId")
    List<User> findUsersByProjectId(@Param("pId") int pId);

    void deleteByUserAndProject(User user, Project project);

    @Modifying
    @Transactional
    @Query("DELETE FROM User_Projects up WHERE up.user.username = :uName AND up.project.ID = :pId")
    void deleteByUsernameAndProjectId(@Param("uName") String uName, @Param("pId") int pId);
}
