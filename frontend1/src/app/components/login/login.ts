import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { JwtTokenStorage } from '../../services/jwt-token-storage';
import { TokenData } from '../../interfaces/token-data';
import { UserService } from '../../services/user-service';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [FormsModule, RouterLink, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  httpClient = inject(HttpClient);
  router = inject(Router);
  jwtStorage = inject(JwtTokenStorage);
  userService = inject(UserService)
  private formBuilder = inject(FormBuilder);
  

  loginForm = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  onSubmit() {
    this.httpClient
      .post<TokenData>('http://localhost:8080/auth/login', this.loginForm.value, {
        observe: 'response',
      })
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
