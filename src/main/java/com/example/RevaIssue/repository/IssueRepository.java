package com.example.RevaIssue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.RevaIssue.entity.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {

}