import {Component, inject, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Button} from 'primeng/button';
import {MasterControlService} from '../../services/master-control.service';
import {HttpClient} from '@angular/common/http';

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
export class AddUserComponent implements OnInit{

  activeCustomers = 0;
  activeVendors: number = 0;
  activePriorityCustomers = 0;

  masterService = inject(MasterControlService)
  http = inject(HttpClient);

  ngOnInit(): void {
    this.getActiveCustomers();
    this.getActiveVendors();
  }

  getActiveCustomers () {
    this.masterService.getCustomers().subscribe((customers) => {
      this.activeCustomers =  customers;
      console.log ('Active customers : ' + this.activeCustomers)
      return customers;
    })
  }

  getActiveVendors () {
    this.masterService.getVendors().subscribe((vendors) => {
      this.activeVendors = vendors;
      console.log ('Active vendors : ' + this.activeVendors)
      return vendors;
    })
  }

  addVendor() {
    this.activeVendors++;

    this.http.post("http://localhost:8080/api/vendor/add-vendor",null).subscribe({
      next: (response) => {
        this.getActiveVendors();
        console.log ('Vendor added successfully');
      },
      error: (e) => {
        console.error('Error adding vendor', e);
      }
    });
  }

  removeVendor() {

    this.activeVendors--;

    this.http.delete("http://localhost:8080/api/vendor/remove-vendor").subscribe({
      next: (response) => {
        this.getActiveVendors();
        console.log ('Vendor removed successfully');
      },
      error: (e) => {
        console.error('Error removing vendor', e);
      }
    });
  }

  addCustomer() {

    this.activeCustomers++;

    this.http.post("http://localhost:8080/api/customer/add-customer",null).subscribe({
      next: (response) => {
        this.getActiveCustomers();
        console.log ('Customer added successfully');
      },
      error: (e) => {
        console.error('Error adding customer', e);
      }
    });
  }

  removeCustomer() {

    this.activeCustomers--;

    this.http.delete("http://localhost:8080/api/customer/remove-customer").subscribe({
      next: (response) => {
        this.getActiveCustomers();
        console.log ('Customer removed successfully');
      },
      error: (e) => {
        console.error('Error removed customer', e);
      }
    });
  }





}
