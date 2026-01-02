import { Injectable, WritableSignal } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { IssueData } from '../interfaces/issue-data';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class IssueService {
  private issueSubject = new BehaviorSubject<IssueData>({
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
    return this.issueSubject;
  }

  viewAllIssues(issues: WritableSignal<Array<IssueData>>, role: 'admin' | 'developer' | 'tester') : void{
      this.httpClient.get<IssueData[]>(`${this.baseUrl}/${role}/issues`)
      .subscribe( issueList => {
        const newIssueList = [];
        for(const issueObj of issueList){
          newIssueList.push(issueObj);
        }
        issues.set(newIssueList);
      });
  }

  loadIssuesForProject(projectId: number): void {}
  createIssue(projectId: number, issue: Partial<IssueData>): void {}
  updateIssue(issueId: number, issue: Partial<IssueData>): void {}
  updateIssueStatus(issueId: number, status: string): void {}
}
