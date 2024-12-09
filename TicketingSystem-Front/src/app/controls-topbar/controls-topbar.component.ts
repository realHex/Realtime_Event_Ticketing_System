import {Component, OnInit} from '@angular/core';
import {Button} from 'primeng/button';
import {ToolbarModule} from 'primeng/toolbar';
import {SplitButtonModule} from 'primeng/splitbutton';
import {BadgeModule} from 'primeng/badge';

@Component({
  selector: 'app-controls-topbar',
  standalone: true,
  imports: [
    Button,
    ToolbarModule,
    SplitButtonModule,
    BadgeModule

  ],
  templateUrl: './controls-topbar.component.html',
  styleUrl: './controls-topbar.component.css',
})
export class ControlsTopbarComponent {


}
