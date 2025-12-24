import { Component, OnDestroy, signal, WritableSignal } from '@angular/core';
import { Subscription } from 'rxjs';
import { IssueService } from '../../services/issue-service';
import { RevaIssueSubscriber } from '../../classes/reva-issue-subscriber';

@Component({
  selector: 'app-issues',
  imports: [],
  templateUrl: './issues.html',
  styleUrl: './issues.css',
})
export class Issues extends RevaIssueSubscriber {
  IssueName: WritableSignal<string> = signal('');

  constructor(private issueService: IssueService) {
    super();
    this.subscription = this.issueService
      .getIssueSubject()
      .subscribe((issueData) => this.IssueName.set(issueData.issue_description));
  }
}
