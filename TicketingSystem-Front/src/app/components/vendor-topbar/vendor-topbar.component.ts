import { Component } from '@angular/core';
import { ToolbarModule } from 'primeng/toolbar';

@Component({
  selector: 'app-vendor-topbar',
  standalone: true,
  imports: [ToolbarModule],
  templateUrl: './vendor-topbar.component.html',
  styleUrl: './vendor-topbar.component.css'
})
export class VendorTopbarComponent {

}
