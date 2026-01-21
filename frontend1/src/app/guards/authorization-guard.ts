import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { JwtTokenStorage } from '../services/jwt-token-storage';
import { HttpClient } from '@angular/common/http';
import { catchError, map, of } from 'rxjs';
import { environment } from '../../environments/environment';

export const authorizationGuard: CanActivateFn = (route, state) => {
  const jwtTokenStorage = inject(JwtTokenStorage);
  const router = inject(Router);
  if(!jwtTokenStorage.getToken()){
    router.navigate(['']);
    return false;
  }
  const httpClient = inject(HttpClient);
  let authorizedPass = false;
  // TODO: grab token after figuring out which controller on the backend will get the request
  return httpClient.get(`${environment.apiUrl}/auth/user`,
    {
      observe:"response",
      headers: {
        Authorization: `Bearer ${jwtTokenStorage.getToken()}`
      }
    }
  ).pipe(
    map( response => {
      authorizedPass = response.status === 204;
      if(!authorizedPass){
        router.navigate(['']);
      }
      return authorizedPass;
    }),
    catchError( () => {
      router.navigate(['']);
      return of(false);
    })
  );
};
