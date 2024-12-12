import { Component, inject, OnInit } from '@angular/core';
import { ChartModule } from 'primeng/chart';
import { LogPollingService } from '../../services/polling/log-polling/log-polling.service';
import { NgForOf } from '@angular/common';

/**
 * LogsComponent is responsible for displaying logs in the UI.
 * It uses a polling service to continuously fetch the latest logs and display them in reverse order.
 */
@Component({
  selector: 'app-logs',
  standalone: true,
  imports: [
    ChartModule,
    NgForOf
  ],
  templateUrl: './logs.component.html',
  styleUrls: ['./logs.component.css']
})
export class LogsComponent implements OnInit {

  // Array to hold the logs received from the polling service
  logs: any[] = [];

  // Injecting the LogPollingService to handle log retrieval
  logPoller = inject(LogPollingService);

  /**
   * ngOnInit lifecycle hook to initiate log polling.
   * This method subscribes to the log polling service and updates the logs array
   * whenever new logs are received.
   */
  ngOnInit(): void {

    this.logPoller.pollLogs(1000).subscribe((updatedLogs: any[]) => {
      // Reverses the logs array so that the latest logs appear first
      this.logs = updatedLogs.reverse();
    });
  }
}
