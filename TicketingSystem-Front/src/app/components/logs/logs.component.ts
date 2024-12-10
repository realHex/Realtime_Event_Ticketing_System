import { Component } from '@angular/core';
import {ChartModule} from 'primeng/chart';

@Component({
  selector: 'app-logs',
  standalone: true,
  imports: [
    ChartModule
  ],
  templateUrl: './logs.component.html',
  styleUrl: './logs.component.css'
})
export class LogsComponent {

}
