import {Component, inject, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Button} from 'primeng/button';
import {MasterControlService} from '../../services/master-control/master-control.service';
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

  masterService = inject(MasterControlService);
  http =  inject(HttpClient);

  ngOnInit(): void {
    this.masterService.vendorNo$.subscribe((vendorNo) => {
      this.activeVendors = vendorNo;
    });

    this.masterService.customerNo$.subscribe((customerNo) => {
      this.activeCustomers = customerNo;
    });

    this.masterService.updateVendors();
    this.masterService.updateCustomers();
  }

  addVendor() {
    this.http.post("http://localhost:8080/api/vendor/add-vendor",null).subscribe({
      next: (response) => {
        this.masterService.updateVendors();
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
        this.masterService.updateVendors();
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
        this.masterService.updateCustomers();
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
        this.masterService.updateCustomers();
        console.log ('Customer removed successfully');
      },
      error: (e) => {
        console.error('Error removed customer', e);
      }
    });
  }


}
