import {inject, Injectable, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {SystemState} from '../../model/class/state';

@Injectable({
  providedIn: 'root'
})
export class MasterControlService implements OnInit{
  private vendorNo = new BehaviorSubject<number>(0);
  private customerNo = new BehaviorSubject<number>(0);
  private priorityCustomerNo = new BehaviorSubject<number>(0);

  private state = new BehaviorSubject<SystemState>(SystemState.STOPPED);

  vendorNo$ = this.vendorNo.asObservable();
  customerNo$ = this.customerNo.asObservable();
  priorityCustomerNo$ = this.priorityCustomerNo.asObservable();

  state$ = this.state.asObservable();

  http =  inject(HttpClient);

  ngOnInit(): void {
    this.updateCustomers();
    this.updateVendors();
    this.updatePriorityCustomers();
    this.updateSystemState();
  }

  updateCustomers(): void {
    this.http.get<number>('http://localhost:8080/api/customer/get-customer').subscribe({
      next: (customerNo) => this.customerNo.next(customerNo),
      error: (e) => console.error('Error trying to get active Customers', e),
    });
  }

  updateVendors(): void {
    this.http.get<number>('http://localhost:8080/api/vendor/get-vendor').subscribe({
      next: (vendorNo) => this.vendorNo.next(vendorNo),
      error: (e) => console.error('Error trying to get active Vendors', e),
    });
  }

  updatePriorityCustomers(): void {
    this.http.get<number>('http://localhost:8080/api/customer/get-priority-customer').subscribe({
      next: (priorityCustomerNo) => this.priorityCustomerNo.next(priorityCustomerNo),
      error: (e) => console.error('Error trying to get active Priority Customers', e),
    });
  }

  getVendorNo(): number {
    return this.vendorNo.getValue();
  }

  getCustomerNo(): number {
    return this.customerNo.getValue();
  }

  getPriorityCustomers(): number {
    return this.priorityCustomerNo.getValue();
  }

  updateSystemState(): void {
    this.http.get<SystemState>('http://localhost:8080/api/system/state').subscribe({
      next: (state) => this.state.next(state),
      error: (e) => console.error('Error trying to get application state', e),
    });
  }

  getSystemState(): SystemState {
    return this.state.getValue();
  }


}
