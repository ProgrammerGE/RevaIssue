import { Component, OnDestroy, signal, WritableSignal } from '@angular/core';
import { RevaIssueSubscriber } from '../../classes/reva-issue-subscriber';
import { ProjectService } from '../../services/project-service';

@Component({
  selector: 'app-projects',
  imports: [],
  templateUrl: './projects.html',
  styleUrl: './projects.css',
})
export class Projects extends RevaIssueSubscriber {
  projectTitle: WritableSignal<string> = signal('');

  // or

  constructor(private projectService: ProjectService) {
    super();
    this.subscription = this.projectService
      .getProjectSubject()
      .subscribe((projectData) => this.projectTitle.set(projectData.project_name));
  }
}
