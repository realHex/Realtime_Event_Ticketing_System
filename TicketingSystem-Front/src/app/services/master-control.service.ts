import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SystemState} from '../model/class/state';

@Injectable({
  providedIn: 'root'
})
export class MasterControlService {

  http =  inject(HttpClient);

  getCustomers(): Observable<number> {
    return this.http.get<number>('http://localhost:8080/api/customer/get-customer');
  }

  getVendors(): Observable<number> {
    return this.http.get<number>('http://localhost:8080/api/vendor/get-vendor');
  }

  getSystemState(): Observable<SystemState> {
    return this.http.get<SystemState>('http://localhost:8080/api/system/state');
  }

}
