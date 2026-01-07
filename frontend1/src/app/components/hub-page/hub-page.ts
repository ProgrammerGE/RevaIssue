import { Component, computed, effect, OnInit, Signal, signal, WritableSignal } from '@angular/core';
import { ListContainer } from '../list-container/list-container';
import { hubListItem } from '../../interfaces/hubpage-list-item';
import { ProjectService } from '../../services/project-service';
import { RevaIssueSubscriber } from '../../classes/reva-issue-subscriber';
import { UserService } from '../../services/user-service';
import { CreateProject } from '../create-project/create-project';
import { FormsModule } from '@angular/forms';
import { AuditLogService } from '../../services/audit-log-service';
import { AuditLogData } from '../../interfaces/audit-log-data';
import { IssueService } from '../../services/issue-service';
import { ProjectData } from '../../interfaces/project-data';
import { IssueData } from '../../interfaces/issue-data';
import { NavBar } from "../nav-bar/nav-bar";

@Component({
  selector: 'app-hub-page',
  imports: [ListContainer, CreateProject, FormsModule, NavBar],
  templateUrl: './hub-page.html',
  styleUrl: './hub-page.css',
})
export class HubPage extends RevaIssueSubscriber {
  username: WritableSignal<string> = signal('');
  userRole: WritableSignal<string> = signal('');
  auditLogs: WritableSignal<Array<AuditLogData>> = signal([]);
  isAdmin: WritableSignal<boolean> = signal(false);
  searchFilter = '';

  constructor(
    private userService: UserService,
    private auditLogService: AuditLogService,
    private issueService: IssueService,
    private projectService: ProjectService // private router: Router
  ) {
    super();
    this.subscription = this.userService.getUserSubject().subscribe((userData) => {
      this.username.set(userData.username);
      this.userRole.set(userData.role.toLowerCase());
    });

    effect(() => {
      const role = this.userRole();
      if (!role) return;

      this.projectService.viewAllProjects(this.projects, role);
    });
  }

  issues: WritableSignal<IssueData[]> = signal([]);
  issuesList: Signal<hubListItem[]> = computed(() => {
    return this.mapIssues(this.issues());
  });
  projects: WritableSignal<ProjectData[]> = signal([]);
  projectsList: Signal<hubListItem[]> = computed(() => {
    return this.mapProject(this.projects());
  });

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
    this.userService.getUserInfo();
    this.getIssues();
    this.isAdmin.set(this.userRole() === 'ADMIN');
    this.auditLogService.getAllAuditLogs(this.auditLogs);
  }

  userLoggedIn: WritableSignal<boolean> = signal(false);

  
    /**
     * I keep getting internal errors from this query function, I commented it out for now.
     */
  filterList(){
    //this.projectService.viewAllProjectsByKeyword(this.searchFilter, this.projects);
    //this.issueService.viewAllIssuesByKeyword(this.searchFilter, this.issues);
  }
}
