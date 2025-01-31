import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { Button } from 'primeng/button';
import { NgClass, NgStyle } from '@angular/common';
import { IManualVendorValues } from '../../model/class/manual-vendor-values';

@Component({
  selector: 'app-vendor-manual-ticketing',
  standalone: true,
  imports: [FormsModule,
    InputTextModule,
    Button,
    NgClass,
    NgStyle,],
  templateUrl: './vendor-manual-ticketing.component.html',
  styleUrl: './vendor-manual-ticketing.component.css'
})
export class VendorManualTicketingComponent {
  formData: IManualVendorValues = {
    ticketAmount: 0,
  };

  validateTicketAmount(): string {
    if (this.formData.ticketAmount <= 0) {
      return 'Ticket amount must be greater than 0';
    }
    return 'Enter the amount of tickets to add';
  }

  validateForm(): boolean {
    return this.formData.ticketAmount > 0;
  }

  onSubmit() {
    if (this.validateForm()) {
      console.log('Form submitted:', this.formData);
      // Add API call here
    }
  }
}
