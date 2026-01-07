import { Component } from '@angular/core';
import { SignoutButton } from "../signout-button/signout-button";

@Component({
  selector: 'app-nav-bar',
  imports: [SignoutButton],
  templateUrl: './nav-bar.html',
  styleUrl: './nav-bar.css',
})
export class NavBar {

}
