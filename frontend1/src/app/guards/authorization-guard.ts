import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { JwtTokenStorage } from '../services/jwt-token-storage';
import { HttpClient } from '@angular/common/http';

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
  return true;
};
