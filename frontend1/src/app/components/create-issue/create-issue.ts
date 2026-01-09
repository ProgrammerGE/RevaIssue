import { Component, Input, signal, WritableSignal } from '@angular/core';
import { PopUpService } from '../../services/pop-up-service';
import { IssueService } from '../../services/issue-service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../services/project-service';
import { ObservableInput } from 'rxjs';

@Component({
  selector: 'app-create-issue',
  imports: [FormsModule],
  templateUrl: './create-issue.html',
  styleUrl: './create-issue.css',
})
export class CreateIssue {
  issueTitle: string = '';
  issueDesc: string = '';
  severityInput: string = '';
  priorityInput: string = '';

  @Input() isPoppedUp: boolean = false;
  projectID: WritableSignal<number> = signal(0);
  buttonText = 'Create Issue';
  buttonCancel = 'Cancel';

  titleMissing: boolean = false;
  descriptionMissing: boolean = false;
  severityMissing: boolean = false;
  priorityMissing: boolean = false;

  constructor(
    private popUpService: PopUpService,
    private issueService: IssueService,
    private router: Router,
    private projectService: ProjectService
  ) {
    this.projectService.getProjectSubject().subscribe((projectData) => {
      this.projectID.set(projectData.projectID);
    });
  }

  addPopup() {
      this.isPoppedUp = true;
  }

  createIssue() {
    //Following the same format as on the project.ts file
    if (
      this.issueTitle != '' &&
      this.issueDesc != '' &&
      this.severityInput != '' &&
      this.priorityInput != ''
    ) {
      this.issueService.createIssue(this.projectID(), {
        name: this.issueTitle,
        description: this.issueDesc,
        severity: Number(this.severityInput),
        priority: Number(this.priorityInput),
      });
      this.isPoppedUp = false;
      this.issueTitle = '';
      this.issueDesc = '';
      this.severityInput = '';
      this.priorityInput = '';
    }

    if (this.issueTitle == '') {
      this.titleMissing = true;
    }

    if (this.issueDesc == '') {
      this.descriptionMissing = true;
    }

    if (this.severityInput == '') {
      this.severityMissing = true;
    }

    if (this.priorityInput == '') {
      this.priorityMissing = true;
    }
  }

  cancelCreation() {
    this.isPoppedUp = false;
    this.issueTitle = '';
    this.issueDesc = '';
    this.severityInput = '';
    this.priorityInput = '';
  }
}
