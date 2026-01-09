import { Component } from '@angular/core';
import { SignoutButton } from "../signout-button/signout-button";
import { RouterLink, RouterModule } from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  imports: [SignoutButton, RouterModule, RouterLink],
  templateUrl: './nav-bar.html',
  styleUrl: './nav-bar.css',
})
export class NavBar {

}
