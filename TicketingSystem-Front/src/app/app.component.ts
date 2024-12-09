import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ApplicationControlsComponent} from './application-controls-topbar/application-controls.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,
    ApplicationControlsComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'TicketingSystem-Front';
}
