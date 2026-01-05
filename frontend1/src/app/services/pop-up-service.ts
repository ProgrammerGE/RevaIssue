import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PopUpService {
  
  private popUpSubjectProject: BehaviorSubject<boolean>;
  private popUpSubjectIssue: BehaviorSubject<boolean>;

  constructor(){
    this.popUpSubjectProject = new BehaviorSubject<boolean>(false);
    this.popUpSubjectIssue = new BehaviorSubject<boolean>(false);
  }
  
  openPopUpProject(){
    this.popUpSubjectProject.next(true);
    this.popUpSubjectIssue.next(false);
  }

  getPopUpProjectSubject(){
    return this.popUpSubjectProject;
  }

  openPopUpIssue(){
    this.popUpSubjectIssue.next(true);
    this.popUpSubjectProject.next(false);
  }

  getPopUpIssueSubject(){
    return this.popUpSubjectIssue;
  }
  
}
