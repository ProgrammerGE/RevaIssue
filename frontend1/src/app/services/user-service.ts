import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { UserData } from '../interfaces/user-data';
import { HttpClient } from '@angular/common/http';
import { JwtTokenStorage } from './jwt-token-storage';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  // get all users method (to be used by admin only)

  // assign user to project method (to be used by admin only)

  // get all users in a project method (to be used by users of that project only)

  // represents the current user (you)
  private UserSubject = new BehaviorSubject<UserData>({
    username: '',
    role: '',
  });

  // represents all users on the app. A list of UserData objects.
  // use this for the admin when they add users to projects
  private listUserSubject = new BehaviorSubject<UserData[]>([]);

  private baseUrl = 'http://localhost:8080';

  constructor(private httpClient: HttpClient, private tokenStorage: JwtTokenStorage) {}

  getUserSubject() {
    return this.UserSubject;
  }

  getUsersSubject() {
    return this.listUserSubject.asObservable();
  }

  fetchUsers(pId: number) {
    const token = this.tokenStorage.getToken();

    this.httpClient
      .get<UserData[]>(`${this.baseUrl}/common/projects/${pId}/users`, {
        // .get<UserData[]>(`${this.baseUrl}/admin/users`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .subscribe({
        next: (users) => this.listUserSubject.next(users),
        error: (err) => console.error('error fetching users', err),
      });
  }

  getUserInfo() {
    const token = this.tokenStorage.getToken();
    console.log('TOKEN VALUE:', token);

    this.httpClient
      .get<UserData>(`${this.baseUrl}/auth/userInfo`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .subscribe({
        next: (userInfo) => {
          console.log('User info received:', userInfo);
          this.UserSubject.next(userInfo);
        },
        error: (err) => console.error('error finding user', err),
      });
  }
}
