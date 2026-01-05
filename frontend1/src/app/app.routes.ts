import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Registration } from './components/registration/registration';
import { HubPage } from './components/hub-page/hub-page';
import { Project } from './components/project/project';
import { Issue } from './components/issue/issue';
import { CreateIssue } from './components/create-issue/create-issue';
import { authorizationGuard } from './guards/authorization-guard';
import { CreateProject } from './components/create-project/create-project';

export const routes: Routes = [
    {path:'', pathMatch:'full', redirectTo:'/login'},
    {path: 'login', component:Login},
    {path: 'register', component:Registration},
    {path: 'hubpage', component:HubPage, canActivate:[authorizationGuard]},
    {path: 'project', component:Project, canActivate:[authorizationGuard]},
    {path: 'issue', component:Issue, canActivate:[authorizationGuard]},
    {path: 'create-issue', component:CreateIssue, canActivate:[authorizationGuard]},
    {path: 'create-project', component:CreateProject, canActivate:[authorizationGuard]}
];
