import { Component, signal, WritableSignal } from '@angular/core';
import { RevaIssueSubscriber } from '../../classes/reva-issue-subscriber';
import { ProjectService } from '../../services/project-service';
import { ProjectData } from '../../interfaces/project-data';

@Component({
  selector: 'app-project',
  imports: [],
  templateUrl: './project.html',
  styleUrl: './project.css',
})
export class Project extends RevaIssueSubscriber {
  project!: ProjectData;
  projectId: number = 0;
  projectTitle: WritableSignal<string> = signal('Sample title');
  projectDescription: WritableSignal<string> = signal('Sample description');
  userRole: 'admin' | 'tester' | 'developer' = 'tester';

  constructor(private projectService: ProjectService) {
    super();
    this.subscription = this.projectService
      .getProjectSubject()
      .subscribe((projectData) => this.projectTitle.set(projectData.projectName));
  }

  viewProject() {
    this.projectService.viewProject(this.projectId, this.userRole);
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
}
