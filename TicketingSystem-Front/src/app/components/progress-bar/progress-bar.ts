import { Component, inject, OnInit } from '@angular/core';
import { ProgressBarModule } from 'primeng/progressbar';
import { PrimeTemplate } from 'primeng/api';
import { CountPollingService } from '../../services/polling/count-polling/count-polling.service';

/**
 * ProgressBarComponent displays a progress bar representing the total tickets
 * and max ticket capacity, with real-time updates provided through polling.
 */
@Component({
  selector: 'app-progress-bar',
  standalone: true,
  imports: [
    ProgressBarModule,
    PrimeTemplate
  ],
  templateUrl: './progress-bar.html',
  styleUrls: ['./progress-bar.css']
})
export class ProgressBarComponent implements OnInit {


  totalTickets = 0;


  maxTicketCapacity = 0;


  polling = inject(CountPollingService);

  /**
   * ngOnInit lifecycle hook to initialize the polling mechanism.
   * It subscribes to the polling service to fetch the total tickets and
   * maximum ticket capacity in real-time.
   */
  ngOnInit(): void {

    this.polling.pollTotalTickets(1000).subscribe({
      next: (tickets) => (this.totalTickets = tickets),  // Update total tickets value
      error: (e) => console.error('Error while polling Total Tickets', e)
    });


    this.polling.pollMaxTicketCapacity(1000).subscribe({
      next: (tickets) => (this.maxTicketCapacity = tickets),  // Update max ticket capacity
      error: (e) => console.error('Error while polling Max Ticket Capacity', e)
    });
  }
}
