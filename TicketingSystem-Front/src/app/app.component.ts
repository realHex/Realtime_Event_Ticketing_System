import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ControlsTopbarComponent} from './controls-topbar/controls-topbar.component';
import {AddUserComponent} from './add-user/add-user.component';
import {ConfigurationFormComponent} from './configuration-form/configuration-form.component';

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
