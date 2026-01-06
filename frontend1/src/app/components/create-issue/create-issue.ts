import { Component, signal, WritableSignal } from '@angular/core';
import { PopUpService } from '../../services/pop-up-service';
import { IssueService } from '../../services/issue-service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-create-issue',
  imports: [FormsModule],
  templateUrl: './create-issue.html',
  styleUrl: './create-issue.css',
})
export class CreateIssue {

  issueTitle: string = "";
  issueDesc: string = "";

  isPoppedUp: WritableSignal<boolean> = signal(false);  
  buttonText = "Create Issue";
  buttonCancel = "Cancel";

  constructor(private popUpService: PopUpService, private issueService: IssueService, private router: Router) {
    this.popUpService.getPopUpIssueSubject().subscribe( popUpSetting => {
      this.isPoppedUp.set(popUpSetting);
    })
  }

  createIssue(){ //Following the same format as on the project.ts file    
    if(this.issueTitle != "" &&  this.issueDesc != ""){
      //Call the createIssue from service, but need the projectID
      this.isPoppedUp.set(false);
    }

  }

  cancelCreation(){
    this.isPoppedUp.set(false);
  }
}
