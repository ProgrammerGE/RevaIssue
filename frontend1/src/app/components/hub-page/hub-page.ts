import { Component, Signal, signal, WritableSignal } from '@angular/core';
import { ListContainer } from '../list-container/list-container';
import { LoginService } from '../../services/login-service';
import { Router, RouterLink } from '@angular/router';
import { hubListItem } from '../../interfaces/hubpage-list-item';
import { ProjectService } from '../../services/project-service';
import { RevaIssueSubscriber } from '../../classes/reva-issue-subscriber';
import { RegistrationService } from '../../services/registration-service';
import { UserService } from '../../services/user-service';
import { CreateProject } from '../create-project/create-project';
import { CreateIssue } from '../create-issue/create-issue';
import { FormsModule } from "@angular/forms";
import { AuditLogService } from '../../services/audit-log-service';
import { AuditLog } from '../../interfaces/audit-log-data';

@Component({
  selector: 'app-hub-page',
  imports: [ListContainer, CreateProject, FormsModule],
  templateUrl: './hub-page.html',
  styleUrl: './hub-page.css',
})
export class HubPage extends RevaIssueSubscriber {
  username: WritableSignal<string> = signal('');
  userRole: WritableSignal<string> = signal('');
  auditLogs: WritableSignal<Array<string>> = signal([]);
  isAdmin: WritableSignal<boolean> = signal(false);
  constructor(
    private userService: UserService,
    private auditLogService: AuditLogService // private router: Router
  ) {
    super();
    this.subscription = this.userService.getUserSubject().subscribe((userData) => {
      this.username.set(userData.username);
      this.userRole.set(userData.role);
    });
    this.auditLogService.getAllAuditLogs(this.auditLogs);
  }

  issuesCount: string = '0';

  projectsList: hubListItem[] = [
    { name: 'Project 1', description: 'this is a description for project 1' },
    { name: 'Project 2', description: 'this is a description for project 2' },
    { name: 'Project 3', description: 'this is a description for project 3' },
  ];

  issuesList: hubListItem[] = [
    { name: 'Issue 1', description: 'this is a description for project 1' },
    { name: 'Issue 2', description: 'this is a description for issue 2' },
    { name: 'Issue 3', description: 'this is a description for issue 3' },
    { name: 'Issue 4', description: 'this is a description for issue 4' },
  ];

  userLoggedIn: WritableSignal<boolean> = signal(false);

  ngOnInit(): void {
    this.userService.getUserInfo();
    console.log("You hit OnInit");
    this.isAdmin.set(this.userRole() === 'ADMIN');
  }
  
  
}
