import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Projects } from './components/projects/projects';
import { Issue } from './components/issue/issue';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'projects', component: Projects },
  { path: 'issues', component: Issue },
];
