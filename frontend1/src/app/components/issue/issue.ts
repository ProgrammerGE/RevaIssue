import { Component, signal, WritableSignal } from '@angular/core';
import { IssueService } from '../../services/issue-service';
import { RevaIssueSubscriber } from '../../classes/reva-issue-subscriber';
import { FormsModule } from '@angular/forms';
import { SignoutButton } from '../signout-button/signout-button';
import { NavBar } from '../nav-bar/nav-bar';

@Component({
  selector: 'app-issue',
  imports: [FormsModule, NavBar],
  templateUrl: './issue.html',
  styleUrl: './issue.css',
})
export class Issue extends RevaIssueSubscriber {
  issueTitle: WritableSignal<string> = signal('');
  issueDescription: WritableSignal<string> = signal('');

  projectId: number = 0;
  issueId: number = 0;

  issueSeverity: number = 0;
  issuePriority: number = 0;

  issueStatus: 'OPEN' | 'IN_PROGRESS' | 'RESOLVED' | 'CLOSED' = 'OPEN';

  issueComments: Comment[] = [];

  // --- UI logic ---
  userRole: 'tester' | 'developer' | 'admin' = 'tester';
  isEditing: boolean = false;

  constructor(private issueService: IssueService) {
    super();
    this.subscription = this.issueService.getIssueSubject().subscribe((issueData) => {
      this.issueTitle.set(issueData.name);
      this.issueDescription.set(issueData.description);
      this.issuePriority = issueData.priority;
      this.issueSeverity = issueData.severity;
      this.issueStatus = issueData.status;
      this.issueId = issueData.issueID;
    });
  }

  viewIssue(): void {
    this.issueService.viewIssueDetails(this.issueId, this.projectId, this.userRole);
  }

  createIssue(): void {
    this.issueService.createIssue(this.projectId, {
      name: this.issueTitle(),
      description: this.issueDescription(),
      priority: this.issuePriority,
      severity: this.issueSeverity,
    });
  }

  updateIssue(): void {
    this.issueService.updateIssue(this.issueId, {
      name: this.issueTitle(),
      description: this.issueDescription(),
      priority: this.issuePriority,
      severity: this.issueSeverity,
    });
  }

  setInProgress(): void {
    this.issueService.updateIssueStatus(this.issueId, /*this.projectId,*/ 'IN_PROGRESS', 'developer');
  }

  resolveIssue(): void {
    this.issueService.updateIssueStatus(this.issueId, /*this.projectId,*/ 'RESOLVED', 'developer');
  }

  closeIssue(): void {
    this.issueService.updateIssueStatus(this.issueId, /*this.projectId,*/ 'CLOSED', 'tester');
  }

  reopenIssue(): void {
    this.issueService.updateIssueStatus(this.issueId, /*this.projectId,*/ 'OPEN', 'tester');
  }

  // --- button logic ---
  onEdit() {
    this.isEditing = true;
  }
  onSubmit() {
    this.isEditing = false;
    alert('Issue updated!');
  }
  onClaim() {
    alert('Issue claimed by you!');
  }
  onResolve() {
    alert('Issue marked as resolved!');
  }
  ngOnInit(): void {
    this.viewIssue();
  }
}
