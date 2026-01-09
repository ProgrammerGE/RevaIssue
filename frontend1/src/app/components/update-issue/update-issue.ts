import { Component, Input, signal, WritableSignal } from '@angular/core';
import { PopUpService } from '../../services/pop-up-service';
import { IssueService } from '../../services/issue-service';
import { ProjectService } from '../../services/project-service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-update-issue',
  imports: [FormsModule],
  templateUrl: './update-issue.html',
  styleUrl: './update-issue.css',
})
export class UpdateIssue {
  //This format will be similar to the create-issue pop up, except it will be used
  // to update the issue

  @Input() issueTitle: string = '';
  @Input() issueDesc: string = '';
  @Input() severityInput: string = '';
  @Input() priorityInput: string = '';
  @Input() issueID: number = 0;

  isPoppedUp: WritableSignal<boolean> = signal(false);
  buttonText = 'Update Issue';
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
    this.popUpService.getPopUpUpdateSubject().subscribe((popUpSetting) => {
      this.isPoppedUp.set(popUpSetting);
    });
  }

  addUpdatePopup() {
    this.popUpService.openUpdatePopup();
  }

  updateIssue() {
    //Following the same format as on the project.ts file
    if (
      this.issueTitle != '' &&
      this.issueDesc != '' &&
      this.severityInput != '' &&
      this.priorityInput != ''
    ) {
      this.issueService.updateIssue(this.issueID, {
        name: this.issueTitle,
        description: this.issueDesc,
        severity: Number(this.severityInput),
        priority: Number(this.priorityInput),
      });
      this.isPoppedUp.set(false);
      this.issueTitle = '';
      this.issueDesc = '';
      this.severityInput = '';
      this.priorityInput = '';
      window.location.reload();
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

  cancelUpdate() {
    this.isPoppedUp.set(false);
    window.location.reload();
  }
}
