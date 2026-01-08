import { Injectable, WritableSignal } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { IssueData } from '../interfaces/issue-data';
import { HttpClient } from '@angular/common/http';
import { JwtTokenStorage } from './jwt-token-storage';

@Injectable({
  providedIn: 'root',
})
export class IssueService {
  private issueSubject = new BehaviorSubject<IssueData>({
    issueID: 0,
    name: '',
    description: '',
    projectID: 0,
    severity: 0,
    priority: 0,
    status: 'OPEN',
    comments: [],
  });

  private baseUrl = 'http://localhost:8080';

  constructor(private httpClient: HttpClient, private tokenStorage: JwtTokenStorage) {}

  getIssueSubject() {
    return this.issueSubject;
  }

  viewIssueDetails(issueId: number, projectId: number, role: string) {
    this.httpClient
      .get<IssueData>(`${this.baseUrl}/${role}/project/${projectId}/issues/${issueId}`)
      .subscribe({
        next: (loadIssue) => this.issueSubject.next(loadIssue),
        error: (err) => console.log(`Error loading issue details`, err),
      });
  }
  createIssue(projectId: number, newIssue: Partial<IssueData>): void {
    const headers = {
      Authorization: `Bearer ${this.tokenStorage.getToken()}`,
    };
    this.httpClient
      .post<IssueData>(`${this.baseUrl}/tester/projects/${projectId}/issues`, newIssue, { headers })
      .subscribe({
        next: (createdIssue) => {
          if (!createdIssue) this.issueSubject.next(createdIssue);
        },
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
    const headers = {
      Authorization: `Bearer ${this.tokenStorage.getToken()}`,
    };
    this.httpClient
      .put<IssueData>(`${this.baseUrl}/${role}/projects/${projectId}/issues/${issueId}`, issue, {
        headers,
      })
      .subscribe({
        next: (updatedIssue) => {
          if (!updatedIssue) this.issueSubject.next(updatedIssue);
        },
        error: (err) => console.log(`Error updating ${issue.name} details`, err),
      });
  }
  updateIssueStatus(
    issueId: number,
    projectId: number,
    status: string,
    role: 'developer' | 'tester'
  ): void {
    const headers = {
      Authorization: `Bearer ${this.tokenStorage.getToken()}`,
    };
    this.httpClient.put<IssueData>(
      `${this.baseUrl}/${role}/projects/${projectId}/issues/${issueId}`,
      { status },
      { headers }
    );
  }

  viewAllIssues(issues: WritableSignal<Array<IssueData>>, role: string): void {
    this.httpClient.get<IssueData[]>(`${this.baseUrl}/${role}/issues`).subscribe((issueList) => {
      const newIssueList = [];
      for (const issueObj of issueList) {
        newIssueList.push(issueObj);
      }
      issues.set(newIssueList);
    });
  }

  getMostRecentIssues(issues: WritableSignal<Array<IssueData>>): void {
    this.httpClient
      .get<IssueData[]>(`${this.baseUrl}/common/issues/latest`)
      .subscribe((issueList) => {
        const newIssueList = [];
        for (const issueObj of issueList) {
          newIssueList.push(issueObj);
        }
        issues.set(newIssueList);
      });
  }

  viewAllIssuesByKeyword(keyword: String, issues: WritableSignal<Array<IssueData>>) {
    this.httpClient
      .get<IssueData[]>(`${this.baseUrl}/common/issues/${keyword}`)
      .subscribe((issueList) => {
        const newIssueList = [];
        for (const issueObj of issueList) {
          newIssueList.push(issueObj);
        }
        issues.set(newIssueList);
      });
  }
}
