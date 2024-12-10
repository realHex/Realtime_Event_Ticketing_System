import {Component, OnInit} from '@angular/core';
import {ProgressBarModule} from 'primeng/progressbar';
import {PrimeTemplate} from 'primeng/api';

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

  totalTickets = 50;
  maxTicketCapacity = 100;

  ngOnInit(): void {
  }



}
