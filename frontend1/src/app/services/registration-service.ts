import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { LoginData } from '../interfaces/login-data';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { JwtTokenStorage } from './jwt-token-storage';
import { TokenData } from '../interfaces/token-data';

@Injectable({
  providedIn: 'root',
})
export class RegistrationService {

  constructor(private httpClient:HttpClient,
    private jwtTokenStorage: JwtTokenStorage,
    private router: Router){}

  registerUser(userData : string, passwordData: string, roleData: string){
    this.httpClient.post<TokenData>(`http://localhost:8080/auth/register`,
      {
        username: userData,
        password: passwordData,
        role: roleData
      },{
        observe: "response"
      }
    )
    .subscribe({
      next:responseData => {
        if(responseData.body){
          this.jwtTokenStorage.setToken(responseData.body.token);
          this.router.navigate(['/hubpage']);
        }
      },
      error: errMsg => {
        console.error(errMsg);
      }
    });
  }

  cancelRegistration(){
    this.router.navigate(['/login']);
  }

}
