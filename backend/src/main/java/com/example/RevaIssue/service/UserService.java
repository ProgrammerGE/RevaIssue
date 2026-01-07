package com.example.RevaIssue.service;

import com.example.RevaIssue.entity.AuditLog;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.entity.User_Projects;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.repository.User_ProjectsRepository;
import com.example.RevaIssue.util.UserDTO;
import com.example.RevaIssue.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, User_ProjectsRepository userProjectsRepository, ProjectRepository projectRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userProjectsRepository = userProjectsRepository;
        this.projectRepository = projectRepository;
        this.userMapper = userMapper;
    }

    public User createUser(User user) {
        user.setUsername(user.getUsername().toLowerCase());
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        // protection for admins
        if (user.getUserRole() == UserRole.ADMIN) {
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

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public List<UserDTO> getAllUsersByProjectId(int pId) {
        // Use the userProjectsRepository to pull all records that contain the pId into a List<User_Projects>

        // Then, use the user id fields from the List<User_Projects> to request a List<User>

        // Then, use the mapper to get a list of List<UserDTO>
        return userProjectsRepository.findUsersByProjectId(pId)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    //
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()  //  List<User>
                .stream()                //  Stream<User>
                .map(userMapper::toDTO)  //  Stream<UserDTO>
                .toList();
    }

    // returns a list of all projects a given user id is associated with
    // TODO: consider moving to project service since this is returning projects
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

    public User_Projects assignProject(int projectId, String userName){
        User user = getUserByUsername(userName);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        // set up the user_projects object
        User_Projects user_projects = new User_Projects();
        user_projects.setUser(user);
        user_projects.setProject(project);
        return userProjectsRepository.save(user_projects);
    }

    public boolean revokeProject(int projectId, String userName) {
        try {
            User user = getUserByUsername(userName);

            // Find the project, or return false if it doesn't exist
            Optional<Project> projectOpt = projectRepository.findById(projectId);
            if (projectOpt.isEmpty()) {
                return false;
            }

            Project project = projectOpt.get();

            // Perform the deletion
            userProjectsRepository.deleteByUserAndProject(user, project);

            return true;
        } catch (Exception e) {
            // Log the error so you know why it failed (e.g., DataIntegrityViolation)
            System.err.println("Failed to revoke project: " + e.getMessage());
            return false;
        }
    }
}
