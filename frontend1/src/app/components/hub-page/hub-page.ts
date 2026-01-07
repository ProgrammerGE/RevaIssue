import { Component, computed, OnInit, Signal, signal, WritableSignal } from '@angular/core';
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
import { FormsModule } from '@angular/forms';
import { AuditLogService } from '../../services/audit-log-service';
import { AuditLog } from '../../interfaces/audit-log-data';
import { IssueService } from '../../services/issue-service';
import { ProjectData } from '../../interfaces/project-data';
import { IssueData } from '../../interfaces/issue-data';

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
    private auditLogService: AuditLogService,
    private issueService: IssueService,
    private projectService: ProjectService // private router: Router
  ) {
    super();
    this.subscription = this.userService.getUserSubject().subscribe((userData) => {
      this.username.set(userData.username);
      this.userRole.set(userData.role);
    });
    this.auditLogService.getAllAuditLogs(this.auditLogs);
  }

  issuesCount: string = '0';
  issues: WritableSignal<IssueData[]> = signal([]);
  issuesList: Signal<hubListItem[]> = computed(() => {
    return this.mapIssues(this.issues());
  });
  projects: WritableSignal<ProjectData[]> = signal([]);
  projectsList: Signal<hubListItem[]> = computed(() => {
    return this.mapProject(this.projects());
  });

  getProjects() {
    this.projectService.viewAllProjects(this.projects, this.userRole());
    this.projectsList = this.projectsList;
  }

  getIssues() {
    this.issueService.getMostRecentIssues(this.issues);
  }

  mapProject(projects: ProjectData[]): hubListItem[] {
    return projects.map((p) => ({
      name: p.projectName,
      description: p.projectDescription,
    }));
  }

  mapIssues(issues: IssueData[]): hubListItem[] {
    return issues.map((i) => ({
      name: i.name,
      description: i.description,
    }));
  }

  ngOnInit() {
    this.getProjects();
    this.getIssues();
    this.userService.getUserInfo();

    this.isAdmin.set(this.userRole() === 'ADMIN');
  }

  userLoggedIn: WritableSignal<boolean> = signal(false);
}
