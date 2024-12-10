import {Component, OnInit} from '@angular/core';
import {Button} from 'primeng/button';

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

  availableTickets = 23;
  addedTickets = 43;
  purchasedTickets = 31;

  ngOnInit(): void {
  }



}
