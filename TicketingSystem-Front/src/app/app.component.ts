import { Component } from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {ControlsTopbarComponent} from './components/controls-topbar/controls-topbar.component';
import {AddUserComponent} from './components/add-user/add-user.component';
import {ConfigurationFormComponent} from './components/configuration-form/configuration-form.component';

import {ToastModule} from 'primeng/toast';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,
    ControlsTopbarComponent,
    AddUserComponent,
    ConfigurationFormComponent],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'TicketingSystem-Front';
}
