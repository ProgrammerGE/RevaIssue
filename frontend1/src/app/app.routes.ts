import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Projects } from './components/projects/projects';
import { Issues } from './components/issues/issues';
import { ProjectDetails } from './components/project-details/project-details';
import { IssueDetails } from './components/issue-details/issue-details';
import { CreateIssue } from './components/create-issue/create-issue';

export const routes: Routes = [
    {path: 'login', component:Login},
    {path: 'projects', component:Projects},
    {path: 'issues', component:Issues},
    // test route, TODO: correct later
    {path: 'project-details', component:ProjectDetails},
    {path: 'issue-details', component:IssueDetails},
    {path: 'create-issue', component:CreateIssue}
];
