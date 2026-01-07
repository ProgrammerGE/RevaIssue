import { Component, inject, Signal, signal, WritableSignal } from '@angular/core';
import { RevaIssueSubscriber } from '../../classes/reva-issue-subscriber';
import { ProjectService } from '../../services/project-service';
import { ProjectData } from '../../interfaces/project-data';
import { ActivatedRoute } from '@angular/router';
import { CreateIssue } from "../create-issue/create-issue";
import { PopUpService } from '../../services/pop-up-service';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { UserService } from '../../services/user-service';
import { UserData } from '../../interfaces/user-data';
import { toSignal } from '@angular/core/rxjs-interop';
import { SignoutButton } from "../signout-button/signout-button";
import { NavBar } from "../nav-bar/nav-bar";

@Component({
  selector: 'app-project',
  imports: [NavBar, FormsModule],
  templateUrl: './project.html',
  styleUrl: './project.css',
})
export class Project extends RevaIssueSubscriber {
  // Dummy data, needs to be grabbed later
  issues = signal([
    {
      id: 101,
      title: 'Login button unresponsive',
      priority: 'High',
      description:
        'The login button on the landing page does not trigger the auth service when clicked on mobile devices.',
    },
    {
      id: 102,
      title: 'CSS Grid misalignment',
      priority: 'Low',
      description: 'The dashboard widgets overlap when the screen resolution is set to 1440p.',
    },
    {
      id: 103,
      title: 'API Timeout on Export',
      priority: 'Medium',
      description:
        'Exporting the user list to CSV times out if the record count exceeds 5,000 entries.',
    },
  ]);
  // This will hold the issue currently being hovered
  hoveredIssue: any = null;

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
  userRole: 'admin' | 'tester' | 'developer' = 'admin';

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

  addUserToProject(): void {
    const username = this.newUser();
    const projectId = this.projectId;

    if (username) {
      this.projectService.addUserToProject(projectId, username);
    }
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
}
