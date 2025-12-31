import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Projects } from './components/projects/projects';
import { Issues } from './components/issues/issues';
import { ProjectDetails } from './components/project-details/project-details';

export const routes: Routes = [
    {path: 'login', component:Login},
    {path: 'projects', component:Projects},
    {path: 'issues', component:Issues},
    {path: 'chris-project', component:ProjectDetails}
];
