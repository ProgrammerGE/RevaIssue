import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Registration } from './components/registration/registration';
import { HubPage } from './components/hub-page/hub-page';
import { Project } from './components/project/project';
import { Issue } from './components/issue/issue';
import { ProjectDetails } from './components/project-details/project-details';
import { CreateIssue } from './components/create-issue/create-issue';

export const routes: Routes = [
    {path:'', pathMatch:'full', redirectTo:'/admin/login'},
    {path: 'login', component:Login},
    {path: 'register', component:Registration},
    {path: 'hubpage', component:HubPage},//TODO: Implement route guards
    {path: 'project', component:Project},
    {path: 'issue', component:Issue},
    // test route, TODO: correct later
    {path: 'project-details', component:ProjectDetails},
    {path: 'create-issue', component:CreateIssue}
];
