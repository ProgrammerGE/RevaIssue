package com.example.RevaIssue.service;

import com.example.RevaIssue.entity.AuditLog;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.entity.User_Projects;
import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.repository.User_ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional //causes every public method to be transactional, meaning that it only succeeds or fails

/*
The user service provides logic for editing the users SQL table, viewing information about users through the
User_Projects SQL table.
 */
public class UserService {
    private final UserRepository userRepository;
    private final User_ProjectsRepository userProjectsRepository;
    @Autowired
    private AuditLogService auditLogService;
    private final ProjectRepository projectRepository;

    public UserService(UserRepository userRepository, User_ProjectsRepository userProjectsRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.userProjectsRepository = userProjectsRepository;
        this.projectRepository = projectRepository;
    }

    public User createUser(User user) {
        // user must have a unique uuid, username.
        // user must have a role set.
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        // protection for admins
        if (user.getUser_Role() == "admin") {
            // TODO: throw an error
        } else {
            // delete the user
            userRepository.delete(user);
        }
    }

    public User getUserById(UUID id) {
        // consider throwing an error instead of returning null
        return userRepository.findById(id).orElse(null);
    }

    // returns a list of all projects a given user id is associated with
    public List<Project> getProjectsById(UUID id) {
        // get the user object
        User user = getUserById(id);
        // query the user_projects repository to get a list of user_projects tables
        List<User_Projects> user_projects = userProjectsRepository.findByUser(user);

        // iterate over user_projects, getting the project object and putting it in the project list to be returned
        List<Project> projects = new ArrayList<>();
        for (User_Projects user_project: user_projects) {
            projects.add(user_project.getProject());
        }

        return projects;
    }

    public User_Projects assignProject(int projectId, UUID uuid){
        User user = getUserById(uuid);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        // set up the user_projects object
        User_Projects user_projects = new User_Projects();
        user_projects.setUser(user);
        user_projects.setProject(project);
        return userProjectsRepository.save(user_projects);
    }
}
