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
  projectTitle: WritableSignal<string> = signal('Sample title');
  projectDescription: WritableSignal<string> = signal('Sample description');
  userRole: 'admin' | 'tester' | 'developer' = 'tester';

  constructor(private projectService: ProjectService, private route: ActivatedRoute) {
    super();
    this.subscription = this.projectService
      .getProjectSubject()
      .subscribe((projectData) => this.projectTitle.set(projectData.project_name));
  }

  ngOnInit(): void {
    this.projectId = Number(this.route.snapshot.paramMap.get('id'));

    if (!this.projectId) {
      throw new Error('Invalid project id');
    }

    this.viewProject();
  }

  viewProject() {
    this.projectService.viewProject(this.projectId, this.userRole);
  }

  updateProject() {
    this.projectService.updateProject(this.projectId, {
      project_name: this.projectTitle(),
      project_description: this.projectDescription(),
    });
  }

  createProject() {
    this.projectService.createProject({
      project_name: this.projectTitle(),
      project_description: this.projectDescription(),
    });
  }
}
