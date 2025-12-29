package com.example.RevaIssue.service;

import com.example.RevaIssue.entity.AuditLog;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.entity.User_Projects;
import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.User_ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private User_ProjectsRepository user_projectsRepo;

    public Project createProject(Project project){
        return projectRepo.save(project);
    }

    public List<Project> getAllProjects(){ return projectRepo.findAll(); }

    public Project getProjectById(int id){ return projectRepo.getReferenceById(id); }

    public boolean deleteProject(int projId){
        Optional<Project> projectOptional = Optional.of(projectRepo.getReferenceById(projId));
        if(!projectOptional.isPresent()){
            return false;
        }
        projectRepo.deleteById(projId);
        return true;
    }

    public Project getProjectById(Integer projectId){
        Optional<Project> projectOptional = Optional.of(projectRepo.getReferenceById(projectId));
        if(projectOptional.isPresent()){
            return projectOptional.get();
        }
        return null; // TODO: Throw error here
    }

    // The Admin can update the project's name and description
    public Project updateProject(int projectId, String name, String description){
        Optional<Project> projectOptional = Optional.of(projectRepo.getReferenceById(projectId));
        if(projectOptional.isPresent()){
            Project projectUpdate = projectOptional.get();
            projectUpdate.setProjectName(name);
            projectUpdate.setProjectDescription(description);
            this.projectRepo.save(projectUpdate);
        }
        return this.projectRepo.getReferenceById(projectId);
    }

    public List<User> getAllUsersByProject(Project project){
        List<User_Projects> userProjectList = user_projectsRepo.findByProject(project);
        List<User> users = new ArrayList<>();
        for(User_Projects userProj : userProjectList){
            users.add(userProj.getUser());
        }
        return users;
    }
}
