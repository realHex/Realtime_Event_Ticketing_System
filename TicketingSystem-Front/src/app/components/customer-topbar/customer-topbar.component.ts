import { Component } from '@angular/core';
import { ToolbarModule } from 'primeng/toolbar';

@Component({
  selector: 'app-customer-topbar',
  standalone: true,
  imports: [ToolbarModule],
  templateUrl: './customer-topbar.component.html',
  styleUrl: './customer-topbar.component.css'
})
export class CustomerTopbarComponent {

}
