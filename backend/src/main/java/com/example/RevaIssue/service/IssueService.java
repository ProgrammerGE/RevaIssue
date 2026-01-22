package com.example.RevaIssue.service;

import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class IssueService {

    @Autowired
    private IssueRepository issueRepo;

    public Issue createIssue(Issue issue){
        return issueRepo.save(issue);
    }

    public Issue getIssue(Long issueId){
        return issueRepo.findById(issueId).orElse(null);
    }

    public List<Issue> getMostRecentIssues(){
        return issueRepo.findTop5ByOrderByDateCreatedDesc();
    }

    public List<Issue> getIssuesByKeyword(String keyword){
        return issueRepo.findByKeyword(keyword);
    }

    public List<Issue> getIssuesByFilter(String status, int severity, int priority){
        return issueRepo.findByFilter(status, severity, priority);
    }

    public List<Issue> getIssuesByProject(Long projectId) {
        // create a list object and assign the data using a call to the repo
        List<Issue> issues = issueRepo.findByProjectProjectID(projectId);
        // return the list
        return issues;
    }

    public Issue updateIssue(Long issueId, Issue updatedIssue){
        return issueRepo.findById(issueId).map(issueUpdate -> {
            issueUpdate.setName(updatedIssue.getName());
            issueUpdate.setDescription(updatedIssue.getDescription());
            issueUpdate.setSeverity(updatedIssue.getSeverity());
            issueUpdate.setPriority(updatedIssue.getPriority());
            return issueRepo.save(issueUpdate);
        }).orElse(null);
    }

    public Issue updateIssueStatus(Long issueId, String status, String role){
        String upperRole = role.toUpperCase();
        String upperStatus = status.toUpperCase();

        // Developer only status updates
        if ((upperStatus.equals("IN_PROGRESS") || upperStatus.equals("RESOLVED")) && !upperRole.equals("DEVELOPER")) {
            throw new RuntimeException("Unauthorized: Role " + upperRole + " cannot set status to " + upperStatus);
        }

        // Tester only status updates
        if ((upperStatus.equals("CLOSED") || upperStatus.equals("OPEN")) && !upperRole.equals("TESTER")) {
            throw new RuntimeException("Unauthorized: Role " + upperRole + " cannot set status to " + upperStatus);
        }

        return issueRepo.findById(issueId).map(targetIssue -> {
            targetIssue.setStatus(upperStatus);
            return issueRepo.save(targetIssue);
        }).orElse(null);
    }


}
