import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {Button} from 'primeng/button';

@Component({
  selector: 'app-configuration-form',
  standalone: true,
  imports: [
    FormsModule,
    InputTextModule,
    Button
  ],
  templateUrl: './configuration-form.component.html',
  styleUrl: './configuration-form.component.css'
})
export class ConfigurationFormComponent {
  totalTickets: number = 0;
  ticketReleaseRate: number = 0;
  customerRetrievalRate: number = 0;
  maxTicketCapacity: number = 0;
}
