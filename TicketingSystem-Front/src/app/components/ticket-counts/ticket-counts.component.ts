import {Component, inject, OnInit} from '@angular/core';
import {Button} from 'primeng/button';
import {CountPollingService} from '../../services/polling/count-polling/count-polling.service';

@Component({
  selector: 'app-ticket-counts',
  standalone: true,
  imports: [
    Button
  ],
  templateUrl: './ticket-counts.component.html',
  styleUrl: './ticket-counts.component.css'
})
export class TicketCountsComponent implements OnInit{

  availableTickets = 0;
  addedTickets = 0;
  purchasedTickets = 0;

  polling = inject(CountPollingService);

  ngOnInit(): void {
    this.polling.pollTotalTickets(1000).subscribe({
      next: (tickets) => (this.availableTickets = tickets),
      error: (e) => console.error('Error while polling Total Tickets', e)
    });

    this.polling.pollAddedTickets(1000).subscribe({
      next: (tickets) => (this.addedTickets = tickets),
      error: (e) => console.error('Error while polling Added Tickets', e)
    });

    this.polling.pollPurchasedTickets(1000).subscribe({
      next: (tickets) => (this.purchasedTickets = tickets),
      error: (e) => console.error('Error while polling Purchased Tickets', e)
    });
  }
}
