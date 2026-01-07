import { Component, inject, Signal, signal, WritableSignal } from '@angular/core';
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

@Component({
  selector: 'app-project',
  imports: [NavBar, FormsModule, CreateIssue],
  templateUrl: './project.html',
  styleUrl: './project.css',
})
export class Project extends RevaIssueSubscriber {
  // Dummy data, needs to be grabbed later
  issues = signal<IssueData[]>([
    {
      issueID: 101,
      name: 'Login button unresponsive',
      description:
        'The login button on the landing page does not trigger the auth service when clicked on mobile devices.',
      projectID: 1,
      severity: 4,
      priority: 3,
      status: 'OPEN',
      comments: [
        {
          author: 'System',
          text: 'Issue created.',
          dateCreated: '2026-01-01T10:00:00Z',
        },
        {
          author: 'Chris Jacobs',
          text: 'Hello world!.',
          dateCreated: '2026-01-02T14:30:00Z',
        },
      ],
    },
    {
      issueID: 102,
      name: 'CSS Grid misalignment',
      description: 'The dashboard widgets overlap when the screen resolution is set to 1440p.',
      projectID: 1,
      severity: 1,
      priority: 1,
      status: 'IN_PROGRESS',
      comments: [],
    },
    {
      issueID: 103,
      name: 'API Timeout on Export',
      description:
        'Exporting the user list to CSV times out if the record count exceeds 5,000 entries.',
      projectID: 2,
      severity: 3,
      priority: 2,
      status: 'OPEN',
      comments: [
        {
          author: 'Backend',
          text: 'Likely related to missing pagination.',
          dateCreated: '2026-01-02T14:30:00Z',
        },
        {
          author: 'Chris Jacobs',
          text: 'Hello world!.',
          dateCreated: '2026-01-02T14:30:00Z',
        },
        {
          author: 'Chris Jacobs',
          text: 'Hello world!.',
          dateCreated: '2026-01-02T14:30:00Z',
        },
        {
          author: 'Chris Jacobs',
          text: 'Hello world!.',
          dateCreated: '2026-01-02T14:30:00Z',
        },
        {
          author: 'Chris Jacobs',
          text: 'Hello world!.',
          dateCreated: '2026-01-02T14:30:00Z',
        },
        {
          author: 'Chris Jacobs',
          text: 'Hello world!.',
          dateCreated: '2026-01-02T14:30:00Z',
        },
      ],
    },
  ]);

  // Dependency Injection
  private projectService = inject(ProjectService);
  private userService = inject(UserService);
  private popUpService = inject(PopUpService);
  private route = inject(ActivatedRoute);

  // vars
  project!: ProjectData;
  projectId: number = 0;
  projectTitle: WritableSignal<string> = signal('');
  projectDescription: WritableSignal<string> = signal('');
  users: Signal<UserData[]> = toSignal(this.userService.getUsersSubject(), { initialValue: [] });
  newUser: WritableSignal<string> = signal('');

  // TODO: Have this update based on the current user
  // userRole: 'admin' | 'tester' | 'developer' = 'tester';
  // userRole: 'admin' | 'tester' | 'developer' = 'admin';
  userRole: 'admin' | 'tester' | 'developer' = 'developer';

  // This will hold the issue currently being hovered
  hoveredIssue: IssueData | null = null;
  // This will hold the issue that was just clicked on
  selectedIssue: IssueData | null = null;

  constructor() {
    super();

    this.subscription = this.projectService.getProjectSubject().subscribe((projectData) => {
      // console.log('EMIT', projectData);
      this.projectTitle.set(projectData.projectName);
      this.projectDescription.set(projectData.projectDescription);
      // console.log('SIGNALS', this.projectTitle(), this.projectDescription());
    });
  }

  ngOnInit(): void {
    this.projectId = Number(this.route.snapshot.paramMap.get('id'));
    console.log('The project id is ', this.projectId);

    if (!this.projectId) {
      throw new Error('Invalid project id');
    }
    this.viewProject();

    this.fetchAndLogUsers();
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

  //Be sure to assign this method to a button in the project html that will be clicked for "creating issues"
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
