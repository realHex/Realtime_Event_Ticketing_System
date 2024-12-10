import { Component } from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {ControlsTopbarComponent} from './components/controls-topbar/controls-topbar.component';
import {AddUserComponent} from './components/add-user/add-user.component';
import {ConfigurationFormComponent} from './components/configuration-form/configuration-form.component';
import {TicketCountsComponent} from './components/ticket-counts/ticket-counts.component';
import {ProgressBarComponent} from './components/progress-bar/progress-bar';
import {ChartComponent} from './components/chart/chart.component';
import {LogsComponent} from './components/logs/logs.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,
    ControlsTopbarComponent,
    AddUserComponent,
    ConfigurationFormComponent,
    TicketCountsComponent,
    ProgressBarComponent,
    ChartComponent,
    LogsComponent],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'TicketingSystem-Front';
}
