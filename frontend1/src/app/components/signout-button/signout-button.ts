import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signout-button',
  imports: [],
  templateUrl: './signout-button.html',
  styleUrl: './signout-button.css',
})
export class SignoutButton {

  constructor(private router: Router){

  }

  signoutRouter(){
    this.router.navigate(['login']);
  }
}
