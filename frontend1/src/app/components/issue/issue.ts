import { Component, signal, WritableSignal } from '@angular/core';
import { IssueService } from '../../services/issue-service';
import { RevaIssueSubscriber } from '../../classes/reva-issue-subscriber';

@Component({
  selector: 'app-issue',
  imports: [],
  templateUrl: './issue.html',
  styleUrl: './issue.css',
})
export class Issue extends RevaIssueSubscriber {
  projectId: number = 0;

  issueId: number = 0;

  issueTitle: WritableSignal<string> = signal('');
  issueDescription: WritableSignal<string> = signal('');

  issueSeverity: number = 0;
  issuePriority: number = 0;

  issueStatus: 'OPEN' | 'IN_PROGRESS' | 'RESOLVED' | 'CLOSED' = 'OPEN';

  issueComments: Comment[] = [];

  constructor(private issueService: IssueService) {
    super();
    this.subscription = this.issueService.getIssueSubject().subscribe((issueData) => {
      this.issueTitle.set(issueData.issue_title);
      this.issueDescription.set(issueData.issue_description);
      this.issuePriority = issueData.priority;
      this.issueSeverity = issueData.severity;
      this.issueStatus = issueData.status;
      this.issueId = issueData.issue_id;
    });
  }

  viewIssues(): void {
    this.issueService.loadIssuesForProject(this.projectId);
  }

  createIssue(): void {
    this.issueService.createIssue(this.projectId, {
      issue_title: this.issueTitle(),
      issue_description: this.issueDescription(),
      priority: this.issuePriority,
      severity: this.issueSeverity,
    });
  }

  updateIssue(): void {
    this.issueService.updateIssue(this.issueId, {
      issue_title: this.issueTitle(),
      issue_description: this.issueDescription(),
      priority: this.issuePriority,
      severity: this.issueSeverity,
    });
  }

  setInProgress(): void {
    this.issueService.updateIssueStatus(this.issueId, 'IN_PROGRESS');
  }

  resolveIssue(): void {
    this.issueService.updateIssueStatus(this.issueId, 'RESOLVED');
  }

  closeIssue(): void {
    this.issueService.updateIssueStatus(this.issueId, 'CLOSED');
  }

  reopenIssue(): void {
    this.issueService.updateIssueStatus(this.issueId, 'OPEN');
  }
}
