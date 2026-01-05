import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PopUpService {
  
  private popUpSubject: BehaviorSubject<boolean>;

  constructor(){
    this.popUpSubject = new BehaviorSubject<boolean>(false);
  }
  
  openPopUp(){
    this.popUpSubject.next(true);
  }

  getPopUpSubject(){
    return this.popUpSubject;
  }
}
