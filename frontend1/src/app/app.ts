import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Login } from './components/login/login';
import { Issues } from './components/issues/issues';
import { Projects } from './components/projects/projects';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Login, Issues, Projects],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('frontend');
}
