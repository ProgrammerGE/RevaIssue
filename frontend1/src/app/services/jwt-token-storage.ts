import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class JwtTokenStorage {
  
  private TOKEN_KEY = 'REVAISSUE_TOKEN';

  setToken(jwt: string){
    localStorage.setItem(this.TOKEN_KEY, jwt);
  }

  getToken(){
    return localStorage.getItem(this.TOKEN_KEY);
  }

  clearToken(){
    localStorage.removeItem(this.TOKEN_KEY);
  }
}
