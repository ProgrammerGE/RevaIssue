import { Component, signal, WritableSignal } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { LoginService } from './services/login-service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink],
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

  }
}
