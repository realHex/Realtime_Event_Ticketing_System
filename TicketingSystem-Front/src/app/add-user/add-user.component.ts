import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Button} from 'primeng/button';

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
export class AddUserComponent {

  metrics = {
    activeVendors : 5,
    activeCustomers : 8,
    activePriorityCustomers : 2
  }

}
