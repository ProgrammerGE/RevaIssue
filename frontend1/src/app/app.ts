import { Component, signal, WritableSignal } from '@angular/core';
import { Router, RouterLinkWithHref, RouterOutlet } from '@angular/router';
import { Login } from './components/login/login';
import { Issues } from './components/issues/issues';
import { Projects } from './components/projects/projects';
import { LoginService } from './services/login-service';
import { SignoutButton } from './components/signout-button/signout-button';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Login, Issues, Projects, SignoutButton],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('frontend');

  userLoggedIn: WritableSignal<boolean> = signal(false);

  constructor(private loginService: LoginService, private router: Router){
    // TODO: call the LoginService to check if the user is logged in
    /**
     * Something like:
     * this.loginService.getLoginSubject().subscribe( loginData => {
     *  this.userLoggedIn.set(loginData.username != "" && loginData.password != "");
     * })
     */
  }

  ngOnInit(): void {
    this.router.navigate([""]);
  }
}
