import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Button } from 'primeng/button';
import { MasterControlService } from '../../services/master-control/master-control.service';
import { HttpClient } from '@angular/common/http';

/**
 * Component for adding and removing users (vendors, customers, and priority customers).
 * Handles user interaction for managing active users and communicating with the backend to update user counts.
 */
@Component({
  selector: 'app-add-user',
  standalone: true,
  imports: [
    FormsModule,
    Button
  ],
  templateUrl: './add-user.component.html',
  styleUrl: './add-user.component.css'
})
export class AddUserComponent implements OnInit {


  activeCustomers = 0;
  activeVendors: number = 0;
  activePriorityCustomers = 0;


  masterService = inject(MasterControlService);
  http = inject(HttpClient);

  /**
   * Initialize the component and subscribe to the master service to track the number of active users.
   * It also updates the count for vendors, customers, and priority customers.
   */
  ngOnInit(): void {
    // Subscribe to vendor, customer, and priority customer numbers and update the UI accordingly
    this.masterService.vendorNo$.subscribe((vendorNo) => {
      this.activeVendors = vendorNo;
    });

    this.masterService.customerNo$.subscribe((customerNo) => {
      this.activeCustomers = customerNo;
    });

    this.masterService.priorityCustomerNo$.subscribe((customerNo) => {
      console.log(customerNo);
      this.activePriorityCustomers = customerNo;
    });

    // Update vendor, customer, and priority customer counts from the server
    this.masterService.updateVendors();
    this.masterService.updateCustomers();
    this.masterService.updatePriorityCustomers();
  }

  /**
   * Add a new vendor to the system.
   * Updates the active vendor count and communicates with the backend to add the vendor.
   */
  addVendor() {
    this.activeVendors++;

    // Send the request to add a vendor to the backend
    this.http.post("http://localhost:8080/api/vendor/add-vendor", null).subscribe({
      next: (response) => {
        // Update the vendors in the master service
        this.masterService.updateVendors();
        console.log('Vendor added successfully');
      },
      error: (e) => {
        console.error('Error adding vendor', e);
      }
    });
  }

  /**
   * Remove a vendor from the system.
   * Decreases the active vendor count and communicates with the backend to remove the vendor.
   */
  removeVendor() {
    this.activeVendors--;

    // Send the request to remove a vendor from the backend
    this.http.delete("http://localhost:8080/api/vendor/remove-vendor").subscribe({
      next: (response) => {
        // Update the vendors in the master service
        this.masterService.updateVendors();
        console.log('Vendor removed successfully');
      },
      error: (e) => {
        console.error('Error removing vendor', e);
      }
    });
  }

  /**
   * Add a new customer to the system.
   * Updates the active customer count and communicates with the backend to add the customer.
   */
  addCustomer() {
    this.activeCustomers++;

    // Send the request to add a customer to the backend
    this.http.post("http://localhost:8080/api/customer/add-customer", null).subscribe({
      next: (response) => {
        // Update the customers in the master service
        this.masterService.updateCustomers();
        console.log('Customer added successfully');
      },
      error: (e) => {
        console.error('Error adding customer', e);
      }
    });
  }

  /**
   * Remove a customer from the system.
   * Decreases the active customer count and communicates with the backend to remove the customer.
   */
  removeCustomer() {
    this.activeCustomers--;

    // Send the request to remove a customer from the backend
    this.http.delete("http://localhost:8080/api/customer/remove-customer").subscribe({
      next: (response) => {
        // Update the customers in the master service
        this.masterService.updateCustomers();
        console.log('Customer removed successfully');
      },
      error: (e) => {
        console.error('Error removed customer', e);
      }
    });
  }

  /**
   * Add a new priority customer to the system.
   * Updates the active priority customer count and communicates with the backend to add the priority customer.
   */
  addPriorityCustomer() {
    this.activePriorityCustomers++;

    // Send the request to add a priority customer to the backend
    this.http.post("http://localhost:8080/api/customer/add-priority-customer", null).subscribe({
      next: (response) => {
        // Update the priority customers in the master service
        this.masterService.updatePriorityCustomers();
        console.log('Priority Customer added successfully');
      },
      error: (e) => {
        console.error('Error adding priority customer', e);
      }
    });
  }

  /**
   * Remove a priority customer from the system.
   * Decreases the active priority customer count and communicates with the backend to remove the priority customer.
   */
  removePriorityCustomer() {
    this.activePriorityCustomers--;

    // Send the request to remove a priority customer from the backend
    this.http.delete("http://localhost:8080/api/customer/remove-priority-customer").subscribe({
      next: (response) => {
        // Update the priority customers in the master service
        this.masterService.updatePriorityCustomers();
        console.log('Priority Customer removed successfully');
      },
      error: (e) => {
        console.error('Error removed priority customer', e);
      }
    });
  }

}
