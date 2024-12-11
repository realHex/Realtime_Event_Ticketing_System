import {Component, inject, OnInit} from '@angular/core';
import {ProgressBarModule} from 'primeng/progressbar';
import {PrimeTemplate} from 'primeng/api';
import {CountPollingService} from '../../services/polling/count-polling/count-polling.service';

@Component({
  selector: 'app-progress-bar',
  standalone: true,
  imports: [
    ProgressBarModule,
    PrimeTemplate
  ],
  templateUrl: './progress-bar.html',
  styleUrl: './progress-bar.css'
})
export class ProgressBarComponent implements OnInit{

  totalTickets = 0;
  maxTicketCapacity = 0;

  polling = inject(CountPollingService);

  ngOnInit(): void {
    this.polling.pollTotalTickets(1000).subscribe({
      next: (tickets) => (this.totalTickets= tickets),
      error: (e) => console.error('Error while polling Total Tickets', e)
    });

    this.polling.pollMaxTicketCapacity(1000).subscribe({
      next: (tickets) => (this.maxTicketCapacity= tickets),
      error: (e) => console.error('Error while polling Max Ticket Capacity', e)
    });
  }



}
