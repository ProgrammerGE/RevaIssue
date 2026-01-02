import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { IssueData } from '../interfaces/issue-data';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class IssueService {
  private issueSubject = new BehaviorSubject<IssueData[]>([]);
  private singleIssueSubject = new BehaviorSubject<IssueData>({
    issue_id: 0,
    issue_title: '',
    issue_description: '',
    project_id: 0,
    severity: 0,
    priority: 0,
    status: 'OPEN',
    comment_chain: [],
  });

  private baseUrl = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) {}

  getIssueSubject() {
    return this.singleIssueSubject;
  }
  viewIssuesForProjects(projectId: number, role: 'admin' | 'developer' | 'tester'): void {
    this.httpClient
      .get<IssueData[]>(`${this.baseUrl}/${role}/projects/${projectId}/issues`)
      .subscribe({
        next: (issues) => this.issueSubject.next(issues),
        error: (err) =>
          console.error(`Error loading issues for project with id: ${projectId}`, err),
      });
  }
  viewIssueDetails(issueId: number, projectId: number, role: string) {
    this.httpClient
      .get<IssueData>(`${this.baseUrl}/${role}/project/${projectId}/issues/${issueId}`)
      .subscribe({
        next: (issue) => this.singleIssueSubject.next(issue),
        error: (err) => console.log('error with viewing issue details', err),
      });
  }
  createIssue(projectId: number, newIssue: Partial<IssueData>): void {
    this.httpClient
      .post<IssueData>(`${this.baseUrl}/tester/projects/${projectId}/issues`, newIssue)
      .subscribe({
        next: (createdIssue) => this.singleIssueSubject.next(createdIssue),
        error: (err) =>
          console.error(`Error creating new issues for project with id: ${projectId}`, err),
      });
  }
  updateIssue(
    issueId: number,
    projectId: number,
    role: 'admin' | 'developer' | 'tester',
    issue: Partial<IssueData>
  ): void {
    this.httpClient
      .put<IssueData>(`${this.baseUrl}/${role}/projects/${projectId}/issues/${issueId}`, issue)
      .subscribe({
        next: (updatedIssue) => {
          //updates the individual issue
          this.singleIssueSubject.next(updatedIssue);
          // updates the list of issues
          this.issueSubject.next(
            this.issueSubject.value.map((i) =>
              i.issue_id === updatedIssue.issue_id ? updatedIssue : i
            )
          );
        },
        error: (err) => console.log('error with updating issue details', err),
      });
  }
  updateIssueStatus(
    issueId: number,
    projectId: number,
    status: string,
    role: 'developer' | 'tester'
  ): void {
    this.httpClient.put<IssueData>(
      `${this.baseUrl}/${role}/projects/${projectId}/issues/${issueId}`,
      { status }
    );
  }
}
