import { Component } from '@angular/core';
import { VendorTopbarComponent } from '../../components/vendor-topbar/vendor-topbar.component';

@Component({
  selector: 'app-vendor',
  standalone: true,
  imports: [VendorTopbarComponent],
  templateUrl: './vendor.component.html',
  styleUrl: './vendor.component.css'
})
export class VendorComponent {

}
