import { Component, effect, Inject, inject, Signal, signal, WritableSignal } from '@angular/core';
import { RevaIssueSubscriber } from '../../classes/reva-issue-subscriber';
import { ProjectService } from '../../services/project-service';
import { ProjectData } from '../../interfaces/project-data';
import { ActivatedRoute } from '@angular/router';
import { CreateIssue } from '../create-issue/create-issue';
import { PopUpService } from '../../services/pop-up-service';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { UserService } from '../../services/user-service';
import { UserData } from '../../interfaces/user-data';
import { toSignal } from '@angular/core/rxjs-interop';
import { SignoutButton } from '../signout-button/signout-button';
import { NavBar } from '../nav-bar/nav-bar';
import { IssueData } from '../../interfaces/issue-data';
import { IssueService } from '../../services/issue-service';

@Component({
  selector: 'app-project',
  imports: [NavBar, FormsModule, CreateIssue],
  templateUrl: './project.html',
  styleUrl: './project.css',
})
export class Project extends RevaIssueSubscriber {
  // load issues for this project
  issues = signal<IssueData[]>([]);

  // Dependency Injection
  private projectService = inject(ProjectService);
  private userService = inject(UserService);
  private popUpService = inject(PopUpService);
  private route = inject(ActivatedRoute);
  private issueService = inject(IssueService);

  // vars
  project!: ProjectData;
  projectId: number = 0;
  projectTitle: WritableSignal<string> = signal('');
  projectDescription: WritableSignal<string> = signal('');
  users: Signal<UserData[]> = toSignal(this.userService.getUsersSubject(), { initialValue: [] });
  newUser: WritableSignal<string> = signal('');

  userRole: WritableSignal<string> = signal(''); // updates based on the current user
  hoveredIssue: IssueData | null = null; // The issue currently being hovered over
  selectedIssue: IssueData | null = null; // The issue that was last clicked on

  constructor() {
    super();

    // sets the user to be used to set issue ability
    this.subscription = this.userService.getUserSubject().subscribe((userData) => {
      if (!userData?.role) return;
      this.userRole.set(userData.role.toLowerCase());
    });

    effect(() => {
      const role = this.userRole();
      if (!role) return;

      this.issueService.viewAllIssues(this.projectId, this.issues, role);
    });
    // shows the current project being selected
    this.subscription.add(
      this.projectService.getProjectSubject().subscribe((projectData) => {
        this.projectTitle.set(projectData.projectName);
        this.projectDescription.set(projectData.projectDescription);
      })
    );
  }

  ngOnInit(): void {
    this.projectId = Number(this.route.snapshot.paramMap.get('id'));
    console.log('The project id is ', this.projectId);

    if (!this.projectId) {
      throw new Error('Invalid project id');
    }
    this.viewProject();

    this.fetchAndLogUsers();
    this.userService.getUserInfo();
  }

  private fetchAndLogUsers(): void {
    console.log('Fetching users...');
    this.userService.fetchUsers(this.projectId);
  }

  private removeUserFromProject(projectId: number, username: string): void {
    this.projectService.removeUserFromProject(projectId, username);
  }

  viewProject() {
    // this.projectService.viewProject(this.projectId, this.userRole);
    this.projectService.viewProject(this.projectId);
  }

  updateProject() {
    this.projectService.updateProject(this.projectId, {
      projectName: this.projectTitle(),
      projectDescription: this.projectDescription(),
    });
  }

  createProject() {
    this.projectService.createProject({
      projectName: this.projectTitle(),
      projectDescription: this.projectDescription(),
    });
  }

  ////////////////////////////////////////////////
  // ISSUES //////////////////////////////////////
  ////////////////////////////////////////////////

  // Logic for the preview pane:
  // Show hover if it exists, otherwise show the sticky selected one
  get displayIssue() {
    return this.hoveredIssue || this.selectedIssue;
  }

  createIssue() {
    this.issueService.createIssue;
  }

  /**
   * This method causes the currently selected issue in the app to update its status.
   * @param status The status for the issue to be set to
   * @param role The role of the current user. This only affects the path of endpoint
   *             that the issue-service.ts will send a patch request to.
   */
  updateIssueStatus(
    status: 'OPEN' | 'IN_PROGRESS' | 'RESOLVED' | 'CLOSED',
    role: 'developer' | 'tester'
  ): void {
    let issueId = this.selectedIssue?.issueID;
    if (issueId) {
      this.issueService.updateIssueStatus(issueId, /*this.projectId,*/ status, role);
    } else {
      console.error('ERROR: cannot access this.selectedIssue?.issueID');
    }
  }

  ////////////////////////////////////////////////
  // BUTTONS /////////////////////////////////////
  ////////////////////////////////////////////////

  /**
   * Triggers from clicking the 'Re-open issue' button in the issue
   * pane of the project view (tester)
   * Sends a request to change the issue to 'open' if it is closed.
   * Otherwise, fails
   */
  reopenIssue() {
    this.updateIssueStatus('OPEN', 'tester');
  }

  /**
   * Triggers from clicking the 'Close issue' button in the issue
   * pane of the project view (tester)
   * Sends a request to change the issue to 'closed'
   */
  closeIssue() {
    this.updateIssueStatus('CLOSED', 'tester');
  }

  /**
   * Triggers from clicking the 'Move to in progress' button in the issue
   * pane of the project view (developer)
   * Sends a request to change the issue to 'in progress'.
   */
  startProgress() {
    this.updateIssueStatus('IN_PROGRESS', 'developer');
  }

  /**
   * Triggers from clicking the 'Resolve Issue' button in the issue
   * pane of the project view (developer)
   * Sends a request to change the issue to 'resolved'.
   */
  resolveIssue() {
    this.updateIssueStatus('RESOLVED', 'developer');
  }

  /**
   * Triggers from clicking an issue in the project view
   * Changes the page's selectedIssue to be what the user clicked on
   * @param issue the issue to be selected
   */
  selectIssue(issue: any) {
    this.selectedIssue = issue;
  }

  /**
   * Triggers from clicking Create Issue in the project view (tester)
   * Opens a popup issue dialog box
   */
  addPopup() {
    this.popUpService.openPopUpIssue();
  }

  /**
   * Triggers from clicking on Add User in the project view (admin)
   * Creates a User_Project record in the database
   */
  addUserToProject(): void {
    const username = this.newUser();
    const projectId = this.projectId;

    if (username) {
      this.projectService.addUserToProject(projectId, username);
    }
  }

  /**
   * Triggers from clicking on users in the users list
   * Deletes a User_Project record from the database
   */
  onUserClick(user: UserData) {
    console.log('removing ', user.username);
    const username = user.username;
    const projectId = this.projectId;
    this.removeUserFromProject(this.projectId, username);
  }
}
