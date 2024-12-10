import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ControlsTopbarComponent} from './components/controls-topbar/controls-topbar.component';
import {AddUserComponent} from './components/add-user/add-user.component';
import {ConfigurationFormComponent} from './components/configuration-form/configuration-form.component';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,
    ControlsTopbarComponent,
    AddUserComponent, ConfigurationFormComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'TicketingSystem-Front';
}
