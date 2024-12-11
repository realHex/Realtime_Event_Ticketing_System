import {Component, inject, OnInit} from '@angular/core';
import {ChartModule} from 'primeng/chart';
import {LogPollingService} from '../../services/polling/log-polling/log-polling.service';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-logs',
  standalone: true,
  imports: [
    ChartModule,
    NgForOf
  ],
  templateUrl: './logs.component.html',
  styleUrl: './logs.component.css'
})
export class LogsComponent implements OnInit{

  logs: any[] = [];

  logPoller = inject(LogPollingService);
  ngOnInit(): void {
    this.logPoller.pollLogs(1000).subscribe((updatedLogs: any[]) => {
      this.logs = updatedLogs.reverse();
    })
  }
}
