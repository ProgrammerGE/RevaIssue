import { Component, signal, WritableSignal } from '@angular/core';
import { RevaIssueSubscriber } from '../../classes/reva-issue-subscriber';
import { ProjectService } from '../../services/project-service';
import { ProjectData } from '../../interfaces/project-data';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-project',
  imports: [],
  templateUrl: './project.html',
  styleUrl: './project.css',
})
export class Project extends RevaIssueSubscriber {
  project!: ProjectData;
  projectId: number = 0;
  projectTitle: WritableSignal<string> = signal('');
  projectDescription: WritableSignal<string> = signal('');

  userRole: 'admin' | 'tester' | 'developer' = 'tester';

  constructor(private projectService: ProjectService, private route: ActivatedRoute) {
    super();
    this.subscription = this.projectService.getProjectSubject().subscribe((projectData) => {
      console.log('EMIT', projectData);
      this.projectTitle.set(projectData.projectName);
      this.projectDescription.set(projectData.projectDescription);
      console.log('SIGNALS', this.projectTitle(), this.projectDescription());
    });
  }

  ngOnInit(): void {
    this.projectId = Number(this.route.snapshot.paramMap.get('id'));

    if (!this.projectId) {
      throw new Error('Invalid project id');
    }

    this.viewProject();
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
}
