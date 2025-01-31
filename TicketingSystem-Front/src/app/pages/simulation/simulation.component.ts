import { Component } from '@angular/core';
import { ControlsTopbarComponent } from '../../components/controls-topbar/controls-topbar.component';
import { AddUserComponent } from '../../components/add-user/add-user.component';
import { ConfigurationFormComponent } from '../../components/configuration-form/configuration-form.component';
import { TicketCountsComponent } from '../../components/ticket-counts/ticket-counts.component';
import { ProgressBarComponent } from '../../components/progress-bar/progress-bar';
import { ChartComponent } from '../../components/chart/chart.component';
import { LogsComponent } from '../../components/logs/logs.component';

@Component({
  selector: 'app-simulation',
  standalone: true,
  imports: [
    ControlsTopbarComponent,
    AddUserComponent,
    ConfigurationFormComponent,
    TicketCountsComponent,
    ProgressBarComponent,
    ChartComponent,
    LogsComponent
  ],
  templateUrl: './simulation.component.html',
  styleUrls: ['./simulation.component.css']
})
export class SimulationComponent {

}
