import { Component, computed, effect, model, ModelSignal, OnInit, Signal, signal, WritableSignal } from '@angular/core';
import { ListContainer } from '../list-container/list-container';
import { hubListItem } from '../../interfaces/hubpage-list-item';
import { ProjectService } from '../../services/project-service';
import { RevaIssueSubscriber } from '../../classes/reva-issue-subscriber';
import { UserService } from '../../services/user-service';
import { FormsModule } from '@angular/forms';
import { AuditLogService } from '../../services/audit-log-service';
import { AuditLogData } from '../../interfaces/audit-log-data';
import { IssueService } from '../../services/issue-service';
import { ProjectData } from '../../interfaces/project-data';
import { IssueData } from '../../interfaces/issue-data';
import { NavBar } from '../nav-bar/nav-bar';
import { CapitalizeFirst } from '../../pipes/capitalize-first.pipe';
import { Router, RouterLink } from '@angular/router';
import { SearchBar } from '../search-bar/search-bar';
import { SearchPopup } from '../search-popup/search-popup';
import { DeleteProject } from '../delete-project/delete-project';
import { UpdateProject } from '../update-project/update-project';
import { ProjectUpdate } from '../../interfaces/project-update';
import { HubMenuContext } from '../../interfaces/hub-menu-context';

@Component({
  selector: 'app-hub-page',
  imports: [
    ListContainer,
    FormsModule,
    NavBar,
    CapitalizeFirst,
    SearchBar,
    RouterLink,
    SearchPopup,
    DeleteProject,
    UpdateProject,
  ],
  templateUrl: './hub-page.html',
  styleUrl: './hub-page.css',
})
export class HubPage extends RevaIssueSubscriber {
  username: WritableSignal<string> = signal('');
  userRole: WritableSignal<string> = signal('');
  userLoggedIn: WritableSignal<boolean> = signal(false);
  auditLogs: WritableSignal<Array<AuditLogData>> = signal([]);
  isAdmin: Signal<boolean> = computed(() => this.userRole().toLowerCase() === 'admin');
  searchFilter = '';
  searchPopupValue = model('');
  isSearchPopupActive = model(false);
  searchResults: WritableSignal<IssueData[]> = signal([]);
  issues: WritableSignal<IssueData[]> = signal([]);
  issuesList: Signal<hubListItem[]> = computed(() => {
    return this.mapIssues(this.issues());
  });
  projects: WritableSignal<ProjectData[]> = signal([]);
  projectsList: Signal<hubListItem[]> = computed(() => {
    return this.mapProject(this.projects());
  });
  isContextMenuActive = model(false);
  contextInfo: WritableSignal<HubMenuContext> = signal<HubMenuContext>({xPos: 0, yPos: 0, item: null});
  isDeleteProjectPopupActive = model(false);
  isUpdatePopupActive = model(false);

  constructor(
    private router: Router,
    private userService: UserService,
    private auditLogService: AuditLogService,
    private issueService: IssueService,
    private projectService: ProjectService,
  ) {
    super();
    this.subscription = this.userService.getUserSubject().subscribe((userData) => {
      if (!userData) return;

      this.username.set(userData.username);
      this.userRole.set(userData.role.toLowerCase());
    });

    effect(() => {
      this.projectService.viewAllProjects(this.projects, this.userRole());
    });

    effect(() => {
      const value = this.searchPopupValue()?.trim();

      if (value) {
        this.issueService.viewAllIssuesByKeyword(value, this.searchResults);
      } else {
        this.searchResults.set([]);
      }
    });
  }

  ngOnInit() {
    this.userService.getUserInfo();
    this.getProjects();
    this.getIssues();
    this.auditLogService.getAllAuditLogs(this.auditLogs);
  }

  goToProject = (item: hubListItem) => {
    this.router.navigate(['/projects', item.id]);
  };

  getProjects() {
    this.projectService.viewAllProjects(this.projects, this.userRole());
    this.projectsList = this.projectsList;
  }

  getIssues() {
    this.issueService.getMostRecentIssues(this.issues);
  }

  mapProject(projects: ProjectData[]): hubListItem[] {
    return projects.map((p) => ({
      id: p.projectID,
      name: p.projectName,
      description: p.projectDescription,
    }));
  }

  mapIssues(issues: IssueData[]): hubListItem[] {
    return issues.map((i) => ({
      id: i.issueID,
      name: i.name,
      description: i.description,
    }));
  }

  contextMenuDelete() {
    this.isContextMenuActive.set(false);
    this.isDeleteProjectPopupActive.set(true);
  }

  contextMenuUpdate() {
    this.isContextMenuActive.set(false);
    this.isUpdatePopupActive.set(true);
  }

  contextEvent(context: HubMenuContext) {
    this.isContextMenuActive.set(true);
    this.contextInfo.set(context);
  }

  closeContextMenu() {
    this.isContextMenuActive.set(false);
  }

  confirmDeletion() {
    const item = this.contextInfo()?.item;
    if (item) {
      this.projectService.deleteProjectByID(item?.id);
    }
    this.isDeleteProjectPopupActive.set(false);
  }

  updateProject(info: ProjectUpdate) {
    const item = this.contextInfo()?.item;
    if (info.title != '' && info.description != '' && item?.id) {
      const newTitle = info.title;
      const newDescription = info.description;

      this.projectService.updateProject(item?.id, {
        projectName: newTitle,
        projectDescription: newDescription,
      });
      window.location.reload();
    }
  }
}