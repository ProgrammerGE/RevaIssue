package com.example.RevaIssue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.RevaIssue.entity.Issue;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

}