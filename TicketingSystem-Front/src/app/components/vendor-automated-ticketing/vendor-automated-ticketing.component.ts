import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { Button } from 'primeng/button';
import { NgClass, NgStyle } from '@angular/common';
import { IVendorAutomated } from '../../model/class/vendor-automated';

@Component({
  selector: 'app-vendor-automated-ticketing',
  standalone: true,
  imports: [
    FormsModule,
    InputTextModule,
    Button,
    NgClass,
    NgStyle,
  ],
  templateUrl: './vendor-automated-ticketing.component.html',
  styleUrl: './vendor-automated-ticketing.component.css'
})
export class VendorAutomatedTicketingComponent {
  formData: IVendorAutomated = {
    ticketAmount: 0,
    ticketReleaseRate: 0
  };

  validateTicketAmount(): string {
    if (this.formData.ticketAmount <= 0) {
      return 'Ticket amount must be greater than 0';
    }
    return 'Enter the amount of tickets to add';
  }

  validateTicketReleaseRate(): string {
    if (this.formData.ticketReleaseRate <= 0) {
      return 'Release rate must be greater than 0';
    }
    return 'Enter tickets to be released per second';
  }

  validateForm(): boolean {
    return this.formData.ticketAmount > 0 && this.formData.ticketReleaseRate > 0;
  }

  onSubmit() {
    if (this.validateForm()) {
      console.log('Form submitted:', this.formData);
      // Add API call here
    }
  }
}
