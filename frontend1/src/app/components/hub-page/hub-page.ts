import { Component, computed, effect, model, OnInit, Signal, signal, WritableSignal } from '@angular/core';
import { ListContainer } from '../list-container/list-container';
import { hubListItem } from '../../interfaces/hubpage-list-item';
import { ProjectService } from '../../services/project-service';
import { RevaIssueSubscriber } from '../../classes/reva-issue-subscriber';
import { UserService } from '../../services/user-service';
import { CreateProject } from '../create-project/create-project';
import { CreateIssue } from '../create-issue/create-issue';
import { FormsModule } from '@angular/forms';
import { AuditLogService } from '../../services/audit-log-service';
import { AuditLogData } from '../../interfaces/audit-log-data';
import { IssueService } from '../../services/issue-service';
import { ProjectData } from '../../interfaces/project-data';
import { IssueData } from '../../interfaces/issue-data';
import { NavBar } from '../nav-bar/nav-bar';
import { CapitalizeFirst } from '../../pipes/capitalize-first.pipe';
import { PopUpService } from '../../services/pop-up-service';
import { Router, RouterLink } from '@angular/router';
import { PopupWrapper } from '../popup-wrapper/popup-wrapper';
import { SearchBar } from '../search-bar/search-bar';
import { SearchPopup } from '../search-popup/search-popup';

@Component({
  selector: 'app-hub-page',
  imports: [
    ListContainer,
    CreateProject,
    FormsModule,
    NavBar,
    CapitalizeFirst,
    PopupWrapper,
    SearchBar,
    RouterLink,
    SearchPopup,
  ],
  templateUrl: './hub-page.html',
  styleUrl: './hub-page.css',
})
export class HubPage extends RevaIssueSubscriber {
  username: WritableSignal<string> = signal('');
  userRole: WritableSignal<string> = signal('');
  auditLogs: WritableSignal<Array<AuditLogData>> = signal([]);
  // made isAdmin a computed signal instead of WritableSignal
  isAdmin: Signal<boolean> = computed(() => this.userRole().toLowerCase() === 'admin');
  searchFilter = '';
  searchPopupValue = model('');
  isSearchPopupActive = model(false);
  searchResults: WritableSignal<IssueData[]> = signal([]);

  constructor(
    private router: Router,
    private userService: UserService,
    private auditLogService: AuditLogService,
    private issueService: IssueService,
    private projectService: ProjectService,
    private popUpService: PopUpService
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

  issues: WritableSignal<IssueData[]> = signal([]);
  issuesList: Signal<hubListItem[]> = computed(() => {
    return this.mapIssues(this.issues());
  });
  projects: WritableSignal<ProjectData[]> = signal([]);
  projectsList: Signal<hubListItem[]> = computed(() => {
    return this.mapProject(this.projects());
  });

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

  closePopup() {
    this.isSearchPopupActive.set(false);
  }

  searchInput(e: Event) {
    if (this.searchPopupValue()?.trim()) {
      this.issueService.viewAllIssuesByKeyword(this.searchPopupValue(), this.searchResults);
    } else {
      this.searchResults.set([]);
    }
  }

  private onKeyDown = (e: KeyboardEvent) => {
    if (e.key === 'Escape') {
      this.isSearchPopupActive.set(false);
    }
  };

  ngOnInit() {
    window.addEventListener('keydown', this.onKeyDown);
    this.userService.getUserInfo();
    this.getProjects();
    this.getIssues();
    this.auditLogService.getAllAuditLogs(this.auditLogs);
  }

  override ngOnDestroy(): void {
    window.removeEventListener('keydown', this.onKeyDown);
  }

  userLoggedIn: WritableSignal<boolean> = signal(false);

  /**
   * I keep getting internal errors from this query function, I commented it out for now.
   */
  filterList() {
    //this.projectService.viewAllProjectsByKeyword(this.searchFilter, this.projects);
    //this.issueService.viewAllIssuesByKeyword(this.searchFilter, this.issues);
  }
}
