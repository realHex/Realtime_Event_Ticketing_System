import { Component } from '@angular/core';
import { VendorTopbarComponent } from '../../components/vendor-topbar/vendor-topbar.component';
import { VendorManualTicketingComponent} from '../../components/vendor-manual-ticketing/vendor-manual-ticketing.component';
import { VendorAutomatedTicketingComponent} from '../../components/vendor-automated-ticketing/vendor-automated-ticketing.component';

@Component({
  selector: 'app-vendor',
  standalone: true,
  imports: [VendorTopbarComponent,
            VendorManualTicketingComponent,
            VendorAutomatedTicketingComponent
  ],
  templateUrl: './vendor.component.html',
  styleUrl: './vendor.component.css'
})
export class VendorComponent {

}
