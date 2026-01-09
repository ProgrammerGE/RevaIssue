import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PopUpService {
  
  private popUpSubjectProject: BehaviorSubject<boolean>;
  private popUpSubjectIssue: BehaviorSubject<boolean>;  
  private popUpSubjectDelete: BehaviorSubject<boolean>;
  private popUpSubjectUpdate: BehaviorSubject<boolean>;

  constructor(){
    this.popUpSubjectProject = new BehaviorSubject<boolean>(false);
    this.popUpSubjectIssue = new BehaviorSubject<boolean>(false);    
    this.popUpSubjectDelete = new BehaviorSubject<boolean>(false);
    this.popUpSubjectUpdate = new BehaviorSubject<boolean>(false);
  }
  
  openPopUpProject(){
    this.popUpSubjectProject.next(true);
    this.popUpSubjectIssue.next(false);
    this.popUpSubjectDelete.next(false);
    this.popUpSubjectUpdate.next(false);
  }

  openDeletingPopup(){     
    this.popUpSubjectDelete.next(true);
    this.popUpSubjectProject.next(false);
    this.popUpSubjectIssue.next(false);
    this.popUpSubjectUpdate.next(false);
  }

   openUpdatePopup(){     
    this.popUpSubjectUpdate.next(true);
    this.popUpSubjectDelete.next(false);
    this.popUpSubjectProject.next(false);
    this.popUpSubjectIssue.next(false);
  }

  openPopUpIssue(){
    this.popUpSubjectIssue.next(true);
    this.popUpSubjectProject.next(false);
    this.popUpSubjectDelete.next(false);
    this.popUpSubjectUpdate.next(false);
  }
  
  
  getPopUpProjectSubject(){
    return this.popUpSubjectProject;
  }

  getPopUpIssueSubject(){
    return this.popUpSubjectIssue;
  }

  getPopUpDeleteSubject(){
    return this.popUpSubjectDelete;
  }

   getPopUpUpdateSubject(){
    return this.popUpSubjectUpdate;
  }
  
}
