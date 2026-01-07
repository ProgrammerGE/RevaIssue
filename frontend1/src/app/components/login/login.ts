import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { JwtTokenStorage } from '../../services/jwt-token-storage';
import { TokenData } from '../../interfaces/token-data';
import { UserService } from '../../services/user-service';

@Component({
  selector: 'app-login',
  imports: [FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  username = '';
  password = '';

  httpClient = inject(HttpClient);
  router = inject(Router);
  jwtStorage = inject(JwtTokenStorage);

  constructor( private userService: UserService,){

  }

  onSubmit() {
    this.httpClient
      .post<TokenData>(
        'http://localhost:8080/auth/login',
        {
          username: this.username,
          password: this.password,
        },
        { observe: 'response' }
      )
      .subscribe({
        next: (response) => {
          if (response.body) {
            const token = response.body.token;
            this.jwtStorage.setToken(token);
            this.userService.getUserInfo();
            this.router.navigate(['/hubpage']);
          }
        },
        error: (err) => {
          console.error(err);
        },
      });
  }
}
