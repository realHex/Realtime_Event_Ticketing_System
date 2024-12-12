import { Component, inject, OnInit } from '@angular/core';
import { Button } from 'primeng/button';
import { CountPollingService } from '../../services/polling/count-polling/count-polling.service';

/**
 * TicketCountsComponent is responsible for displaying the number of available,
 * added, and purchased tickets in real-time by polling the relevant data from
 * the backend at regular intervals.
 */
@Component({
  selector: 'app-ticket-counts',
  standalone: true,
  imports: [
    Button
  ],
  templateUrl: './ticket-counts.component.html',
  styleUrls: ['./ticket-counts.component.css']
})
export class TicketCountsComponent implements OnInit {


  availableTickets = 0;
  addedTickets = 0;
  purchasedTickets = 0;


  polling = inject(CountPollingService);

  /**
   * ngOnInit lifecycle hook to start polling the data on component initialization.
   * It subscribes to the CountPollingService to fetch the current values of
   * available, added, and purchased tickets at regular intervals (every 1000 ms).
   */
  ngOnInit(): void {

    this.polling.pollTotalTickets(1000).subscribe({
      next: (tickets) => (this.availableTickets = tickets),  // Update available tickets
      error: (e) => console.error('Error while polling Total Tickets', e)
    });


    this.polling.pollAddedTickets(1000).subscribe({
      next: (tickets) => (this.addedTickets = tickets),  // Update added tickets
      error: (e) => console.error('Error while polling Added Tickets', e)
    });


    this.polling.pollPurchasedTickets(1000).subscribe({
      next: (tickets) => (this.purchasedTickets = tickets),  // Update purchased tickets
      error: (e) => console.error('Error while polling Purchased Tickets', e)
    });
  }
}
