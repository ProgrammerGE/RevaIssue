import { Component, OnDestroy, signal, WritableSignal } from '@angular/core';
import { Subscription } from 'rxjs';
import { IssueService } from '../../services/issue-service';
import { RevaIssueSubscriber } from '../../classes/reva-issue-subscriber';

@Component({
  selector: 'app-issue',
  imports: [],
  templateUrl: './issue.html',
  styleUrl: './issue.css',
})
export class Issue extends RevaIssueSubscriber {
  IssueName: WritableSignal<string> = signal('');
  IssueId: number = 0;
  IssueDescription: WritableSignal<string> = signal('');
  IssueSeverity: number = 0;
  IssuePriority: number = 0;
  IssueStatus: 'OPEN' | 'IN_PROGRESS' | 'RESOLVED' | 'CLOSED' = 'OPEN';
  IssueComments!: Array<Comment>;

  constructor(private issueService: IssueService) {
    super();
    this.subscription = this.issueService
      .getIssueSubject()
      .subscribe((issueData) => this.IssueName.set(issueData.issue_description));
  }
}
