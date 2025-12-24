import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { IssueData } from '../interfaces/issue-data';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class IssueService {
  private issueSubject: BehaviorSubject<IssueData>;

  // wont be httpClient it will talk to backend
  constructor(private httpClient: HttpClient) {
    this.issueSubject = new BehaviorSubject<IssueData>({
      issue_id: 0,
      issue_description: '',
      project_id: 0,
      severity: 0,
      priority: 0,
      status: '',
      comment_chain: [],
    });
  }

  getIssueSubject() {
    return this.issueSubject;
  }
}
