import { Component, Input, signal, WritableSignal } from '@angular/core';
import { Project } from '../project/project';
import { Issue } from '../issue/issue';
import { SignoutButton } from '../signout-button/signout-button';
import { LoginService } from '../../services/login-service';
import { Router, RouterLink } from '@angular/router';
import { ProjectService } from '../../services/project-service';
import { IssueService } from '../../services/issue-service';
import { AuditLogService } from '../../services/audit-log-service';
import { AuditLog } from '../../interfaces/audit-log-data';
import { ProjectData } from '../../interfaces/project-data';
import { IssueData } from '../../interfaces/issue-data';

@Component({
  selector: 'app-hub-page',
  imports: [RouterLink],
  templateUrl: './hub-page.html',
  styleUrl: './hub-page.css',
})
export class HubPage {

  userLoggedIn: WritableSignal<boolean> = signal(false);

  username!: string;  
  role!: 'admin' | 'tester' | 'developer';
  
  projects: WritableSignal<Array<ProjectData>> = signal([]);
  issues: WritableSignal<Array<IssueData>> = signal([]);
  auditLogs: WritableSignal<Array<AuditLog>> = signal([]);

  constructor(private projectService: ProjectService, 
    private issueService: IssueService, 
    private auditLogService: AuditLogService,
    private router: Router){
    this.projectService.viewAllProjects(this.projects, this.role);
    this.issueService.viewAllIssues(this.issues, this.role);
    this.auditLogService.getAllAuditLogs(this.auditLogs);
  }

  moveToProject(id: string){
    console.log("Takes you to project");
    this.router.navigate([`/projects/${id}`]);
  }

  moveToIssue(id: string){
    console.log("Takes you to project");
    this.router.navigate([`/issues/${id}`]);
  }
}
