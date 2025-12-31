import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Projects } from './components/projects/projects';
import { Issues } from './components/issues/issues';
import { Registration } from './components/registration/registration';
import { HubPage } from './components/hub-page/hub-page';

export const routes: Routes = [
    {path:'', pathMatch:'full', redirectTo:'/admin/login'},
    {path: 'login', component:Login},
    {path: 'register', component:Registration},
    {path: 'hubpage', component:HubPage},//TODO: Implement route guards
    {path: 'projects', component:Projects}, //TODO: Implement route guards
    {path: 'issues', component:Issues} //TODO: Implement route guards
];
