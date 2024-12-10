import {Component, inject, OnInit} from '@angular/core';
import {Button} from 'primeng/button';
import {ToolbarModule} from 'primeng/toolbar';
import {SplitButtonModule} from 'primeng/splitbutton';
import {BadgeModule} from 'primeng/badge';
import {SystemState} from '../../model/class/state';
import {MasterControlService} from '../../services/master-control.service';
import {HttpClient} from '@angular/common/http';

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
export class ControlsTopbarComponent implements OnInit{

  systemState: SystemState = SystemState.STOPPED;
  buttonLabel: string = 'Start';

  http = inject(HttpClient);
  masterService = inject(MasterControlService)


  ngOnInit(): void {
    this.getSystemState();
  }

  getSystemState () {
    this.masterService.getSystemState().subscribe((systemState) => {
      this.systemState =  systemState;
      console.log ('The system is currently ' + systemState)
    })
  }

  start() {
    this.systemState = SystemState.RUNNING;

    this.http.post("http://localhost:8080/api/system/start", null).subscribe({
      next: (response) => {
        this.getSystemState();
        console.log ('System started');
      },
      error: (e) => {
        console.error('System encountered an error while starting', e);
      }
    })
  }

  stop() {
    this.systemState = SystemState.PAUSED;

    this.http.post("http://localhost:8080/api/system/stop", null).subscribe({
      next: (response) => {
        this.getSystemState();
        this.buttonLabel = 'Resume';
        console.log ('System stopped');
      },
      error: (e) => {
        console.error('System encountered an error while stopping', e);
      }
    })
  }

  reset() {
    this.systemState = SystemState.STOPPED;

    this.http.post("http://localhost:8080/api/system/reset", null).subscribe({
      next: (response) => {
        this.getSystemState();
        this.buttonLabel = 'Start';
        console.log ('System reset');
      },
      error: (e) => {
        console.error('System encountered an error while resetting', e);
      }
    })
  }


  protected readonly SystemState = SystemState;
}
