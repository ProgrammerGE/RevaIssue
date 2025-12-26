package com.example.RevaIssue.service;

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

    public boolean deleteProject(int projId){
        Project targetProject = projectRepo.getReferenceById(projId);
        if(targetProject == null){
            return false;
        }
        projectRepo.deleteById(projId);
        return true;
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
