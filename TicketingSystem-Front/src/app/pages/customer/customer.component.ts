import { Component } from '@angular/core';
import { CustomerTopbarComponent } from '../../components/customer-topbar/customer-topbar.component';

@Component({
  selector: 'app-customer',
  standalone: true,
  imports: [CustomerTopbarComponent],
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.css'
})
export class CustomerComponent {

}
