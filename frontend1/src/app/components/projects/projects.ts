import { Component, OnDestroy } from '@angular/core';
import { RevaIssueSubscriber } from '../../classes/reva-issue-subscriber';

@Component({
  selector: 'app-projects',
  imports: [],
  templateUrl: './projects.html',
  styleUrl: './projects.css',
})
export class Projects extends RevaIssueSubscriber {}
