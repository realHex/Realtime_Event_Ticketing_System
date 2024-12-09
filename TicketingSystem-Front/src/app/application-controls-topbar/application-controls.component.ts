import {Component, ViewEncapsulation} from '@angular/core';
import {Button} from 'primeng/button';

@Component({
  selector: 'app-application-controls',
  standalone: true,
  imports: [
    Button
  ],
  templateUrl: './application-controls.component.html',
  styleUrl: './application-controls.component.css',
}
)
export class ApplicationControlsComponent {

}
