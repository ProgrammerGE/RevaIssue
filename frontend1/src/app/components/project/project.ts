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

  // updates based on the current user
  userRole: WritableSignal<string> = signal('');

  // This will hold the issue currently being hovered
  hoveredIssue: IssueData | null = null;
  // This will hold the issue that was just clicked on
  selectedIssue: IssueData | null = null;

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

      this.issueService.viewAllIssues(this.issues, role);
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

  /**
   * Function called by project.html
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
   * Function called by project.html
   * Triggers from clicking on users in the users list
   * Deletes a User_Project record from the database
   */
  onUserClick(user: UserData) {
    console.log('removing ', user.username);
    const username = user.username;
    const projectId = this.projectId;
    this.removeUserFromProject(this.projectId, username);
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

  addPopup() {
    this.popUpService.openPopUpIssue();
  }

  // Helper method to handle the click
  selectIssue(issue: any) {
    this.selectedIssue = issue;
  }

  // Logic for the preview pane:
  // Show hover if it exists, otherwise show the sticky selected one
  get displayIssue() {
    return this.hoveredIssue || this.selectedIssue;
  }
}
