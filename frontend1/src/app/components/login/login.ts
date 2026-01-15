import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { JwtTokenStorage } from '../../services/jwt-token-storage';
import { TokenData } from '../../interfaces/token-data';
import { UserService } from '../../services/user-service';
import { LoginForm } from '../../interfaces/login-form';
import { form, Field } from '@angular/forms/signals';

@Component({
  selector: 'app-login',
  imports: [FormsModule, RouterLink, Field],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  loginModel = signal<LoginForm>({
    username: '',
    password: '',
  });

  loginForm = form(this.loginModel, (scemaPath) => {
    
  });
  httpClient = inject(HttpClient);
  router = inject(Router);
  jwtStorage = inject(JwtTokenStorage);

  constructor(private userService: UserService) {}

  onSubmit() {
    console.log(this.loginForm.username().value())
    this.httpClient
      .post<TokenData>(
        'http://localhost:8080/auth/login',
        {
          username: this.loginForm.username().value(),
          password: this.loginForm.password().value(),
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
