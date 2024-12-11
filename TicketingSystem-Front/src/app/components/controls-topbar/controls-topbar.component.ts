import {Component, inject, OnInit} from '@angular/core';
import {Button, ButtonModule} from 'primeng/button';
import {ToolbarModule} from 'primeng/toolbar';
import {SplitButtonModule} from 'primeng/splitbutton';
import {BadgeModule} from 'primeng/badge';
import {SystemState} from '../../model/class/state';
import {MasterControlService} from '../../services/master-control/master-control.service';
import {HttpClient} from '@angular/common/http';
import {ToastrService} from 'ngx-toastr';
import {RippleModule} from 'primeng/ripple';
import {ToastModule} from 'primeng/toast';

@Component({
  selector: 'app-controls-topbar',
  standalone: true,
  imports: [
    Button,
    ToolbarModule,
    SplitButtonModule,
    BadgeModule,
    ToastModule,
    ButtonModule,
    RippleModule
  ],
  templateUrl: './controls-topbar.component.html',
  styleUrl: './controls-topbar.component.css',
})
export class ControlsTopbarComponent implements OnInit{

  systemState: SystemState = SystemState.STOPPED;
  buttonLabel: string = 'Start';

  http = inject(HttpClient);
  masterService = inject(MasterControlService);
  toaster = inject(ToastrService);



  ngOnInit(): void {
    this.masterService.state$.subscribe((state) => {
      this.systemState = state;
    });
    this.masterService.updateSystemState();

  }

  start() {
    this.http.post("http://localhost:8080/api/system/start", null).subscribe({
      next: (response) => {
        this.masterService.updateSystemState();
        if(this.masterService.getVendorNo()===0 && this.masterService.getCustomerNo()===0) {
          this.toaster.warning('Add vendors or customers before starting','Not enough Users')
        } else if(this.masterService.getSystemState()===SystemState.STOPPED){
          this.toaster.success('Simulation Started')
        } else {
          this.toaster.success('Simulation Resumed')
      }},
      error: (e) => {
        console.error('System encountered an error while starting', e);
      }
    });
  }

  stop() {
    this.http.post("http://localhost:8080/api/system/stop", null).subscribe({
      next: (response) => {
        this.masterService.updateSystemState();
        this.buttonLabel = 'Resume';
        this.toaster.info('Simulation Stopped')
        console.log ('System stopped');
      },
      error: (e) => {
        console.error('System encountered an error while stopping', e);
      }
    })
  }

  reset() {
    this.http.post("http://localhost:8080/api/system/reset", null).subscribe({
      next: (response) => {
        this.masterService.updateSystemState();
        this.masterService.updateVendors();
        this.masterService.updateCustomers();
        this.masterService.updatePriorityCustomers();
        this.buttonLabel = 'Start';
        this.toaster.warning('Simulation Reset');
        console.log ('System reset');
      },
      error: (e) => {
        console.error('System encountered an error while resetting', e);
      }
    })
  }


  protected readonly SystemState = SystemState;
}
