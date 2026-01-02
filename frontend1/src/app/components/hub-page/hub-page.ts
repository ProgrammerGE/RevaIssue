import { Component, signal, WritableSignal } from '@angular/core';
// import { Issues } from '../issues/issues';
import { SignoutButton } from '../signout-button/signout-button';
import { LoginService } from '../../services/login-service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-hub-page',
  imports: [RouterLink],
  templateUrl: './hub-page.html',
  styleUrl: './hub-page.css',
})
export class HubPage {

  userLoggedIn: WritableSignal<boolean> = signal(false);

  constructor(private loginService: LoginService, private router: Router){}
}
