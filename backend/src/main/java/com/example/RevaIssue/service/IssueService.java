package com.example.RevaIssue.service;

import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.helper.Comment;
import com.example.RevaIssue.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IssueService {

    @Autowired
    private IssueRepository issueRepo;

    public Issue createIssue(Issue issue){
        return issueRepo.save(issue);
    }

    public Issue getIssue(Long issueId){
        return issueRepo.getReferenceById(issueId);
    }

    // TODO: updating the issue's description, comment, severity, and priority, status
    public Issue updateIssueDescription(Long issueId, String desc){
        Issue targetIssue = issueRepo.getReferenceById(issueId);
        if(desc.isEmpty()){
            return null;
        }
        targetIssue.setDescription(desc);
        issueRepo.save(targetIssue);
        return issueRepo.getReferenceById(issueId);
    }

    public Issue updateIssueSeverity(Long issueId, Integer severity){
        Issue targetIssue = issueRepo.getReferenceById(issueId);
        targetIssue.setSeverity(severity);
        issueRepo.save(targetIssue);
        return issueRepo.getReferenceById(issueId);
    }

    public Issue updateIssuePriority(Long issueId, Integer priority){
        Issue targetIssue = issueRepo.getReferenceById(issueId);
        targetIssue.setPriority(priority);
        issueRepo.save(targetIssue);
        return issueRepo.getReferenceById(issueId);
    }

    public Issue updateIssueStatus(Long issueId, String status){
        Issue targetIssue = issueRepo.getReferenceById(issueId);
        targetIssue.setStatus(status);
        issueRepo.save(targetIssue);
        return issueRepo.getReferenceById(issueId);
    }

    public Issue updateIssueComment(Long issueId, String comment){
        Issue targetIssue = issueRepo.getReferenceById(issueId);
        targetIssue.getComment().setText(comment);
        issueRepo.save(targetIssue);
        return targetIssue;
    }

    public Comment createIssueComment(Long issueId, Comment comment){
        Issue targetIssue = issueRepo.getReferenceById(issueId);
        targetIssue.setComment(comment);
        return targetIssue.getComment();
    }

    public Comment getIssueComment(Long issueId){
        return issueRepo.getReferenceById(issueId).getComment();
    }

}
