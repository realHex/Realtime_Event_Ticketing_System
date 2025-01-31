import { Routes } from '@angular/router';
import { SimulationComponent } from './pages/simulation/simulation.component';
import { VendorComponent } from './pages/vendor/vendor.component';
import { CustomerComponent } from './pages/customer/customer.component';

export const routes: Routes = [
  { path: '', redirectTo: 'simulation', pathMatch: 'full' },
  { path: 'simulation', component: SimulationComponent },
  { path: 'vendor', component: VendorComponent },
  { path: 'customer', component: CustomerComponent }
];
