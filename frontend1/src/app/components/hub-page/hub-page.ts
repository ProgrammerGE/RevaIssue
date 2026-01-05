import { Component, signal, WritableSignal } from '@angular/core';
import { ListContainer } from '../list-container/list-container';
import { Project } from '../project/project';
import { SignoutButton } from '../signout-button/signout-button';
import { LoginService } from '../../services/login-service';
import { Router, RouterLink } from '@angular/router';
import { hubListItem } from '../../interfaces/hubpage-list-item';
import { CreateProject } from "../create-project/create-project";

@Component({
  selector: 'app-hub-page',
  imports: [RouterLink, ListContainer, CreateProject],
  templateUrl: './hub-page.html',
  styleUrl: './hub-page.css',
})
export class HubPage {
  username: string = 'username';
  userRole: string = 'role';
  issuesCount: string = '0';

  projectsList: hubListItem[] = [
    { name: 'Project 1', description: 'this is a description for project 1' },
    { name: 'Project 2', description: 'this is a desription for project 2' },
    { name: 'Project 3', description: 'this is a description for projet 3' },
  ];

  issuesList: hubListItem[] = [
    { name: 'Issue 1', description: 'this is a description for project 1' },
    { name: 'Issue 2', description: 'this is a desription for issue 2' },
    { name: 'Issue 3', description: 'this is a description for issue 3' },
    { name: 'Issue 4', description: 'this is a description for issue 4' },
  ];

  userLoggedIn: WritableSignal<boolean> = signal(false);

  constructor(private loginService: LoginService, private router: Router) {}
}
