import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { NgClass, NgStyle } from '@angular/common';
import { IAutomatedVendorValues } from '../../model/class/automated-vendor-values';

@Component({
  selector: 'app-vendor-automated-ticketing',
  standalone: true,
  imports: [
    FormsModule,
    CheckboxModule,
    InputTextModule,
    ButtonModule,
    NgClass,
    NgStyle
  ],
  templateUrl: './vendor-automated-ticketing.component.html',
  styleUrls: ['./vendor-automated-ticketing.component.scss']
})
export class VendorAutomatedTicketingComponent {
  formData: IAutomatedVendorValues = {
    ticketAmount: 0,
    ticketReleaseRate: 0,
    hasTicketLimit: false,
    ticketLimit: 0
  };

  randomVariable: boolean = true;
  isAddingTickets: boolean = false;

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

  validateTicketLimit(): string {
    if (this.formData.ticketLimit <= 0) {
      return 'Ticket limit must be greater than 0';
    }
    return 'Enter the amount of tickets to be released';
  }


  validateForm(): boolean {
    return this.formData.ticketAmount > 0 && this.formData.ticketReleaseRate > 0;
  }
  
  showTicketLimitInput(): void {
    console.log('Checkbox state:', this.formData.hasTicketLimit);
    // Add any additional logic you need when the checkbox changes
  }

  onSubmit(): void {
    if (this.validateForm()) {
      console.log('Form submitted:', this.formData);
      // Add API call here
    }
  }

  onStartAdding() {
    this.isAddingTickets = true;

  }

  onStopAdding() {
    this.isAddingTickets = false;

  }
}
