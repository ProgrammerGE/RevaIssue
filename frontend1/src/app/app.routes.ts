import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Projects } from './components/projects/projects';
import { Issues } from './components/issues/issues';

export const routes: Routes = [
    {path: 'login', component:Login},
    {path: 'projects', component:Projects},
    {path: 'issues', component:Issues}
];
