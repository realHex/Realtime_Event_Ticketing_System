import { Component, inject, OnInit } from '@angular/core';
import { Button, ButtonModule } from 'primeng/button';
import { ToolbarModule } from 'primeng/toolbar';
import { SplitButtonModule } from 'primeng/splitbutton';
import { BadgeModule } from 'primeng/badge';
import { SystemState } from '../../model/class/state';
import { MasterControlService } from '../../services/master-control/master-control.service';
import { HttpClient } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { RippleModule } from 'primeng/ripple';
import { ToastModule } from 'primeng/toast';

/**
 * ControlsTopbarComponent is responsible for the top toolbar in the system.
 * It manages the simulation's start, stop, and reset operations, updating the system state accordingly.
 * The component also handles the display of relevant notifications for simulation actions.
 */
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
  styleUrls: ['./controls-topbar.component.css'],
})
export class ControlsTopbarComponent implements OnInit {


  systemState: SystemState = SystemState.STOPPED;


  buttonLabel: string = 'Start';


  http = inject(HttpClient);
  masterService = inject(MasterControlService);
  toaster = inject(ToastrService);

  /**
   * Initializes the component by subscribing to the system state changes
   * and updates the state on component initialization.
   */
  ngOnInit(): void {
    // Subscribe to system state changes
    this.masterService.state$.subscribe((state) => {
      this.systemState = state;
    });

    // Update the system state initially
    this.masterService.updateSystemState();
  }

  /**
   * Starts or resumes the simulation.
   * Makes an HTTP request to start the system and updates the system state accordingly.
   * Displays relevant notifications based on conditions like missing vendors/customers or system state.
   */
  start() {
    this.http.post("http://localhost:8080/api/system/start", null).subscribe({
      next: (response) => {
        // Update the system state after the start request
        this.masterService.updateSystemState();


        if (this.masterService.getVendorNo() === 0 && this.masterService.getCustomerNo() === 0) {
          // Display warning if there are no vendors or customers
          this.toaster.warning('Add vendors or customers before starting', 'Not enough Users');
        } else if (this.masterService.getSystemState() === SystemState.STOPPED) {
          // Display success message if the simulation starts
          this.toaster.success('Simulation Started');
        } else {
          // Display success message if the simulation is resumed
          this.toaster.success('Simulation Resumed');
        }
      },
      error: (e) => {

        console.error('System encountered an error while starting', e);
      }
    });
  }

  /**
   * Stops the simulation and updates the system state.
   * Changes the button label to 'Resume' and displays an informational message.
   */
  stop() {
    this.http.post("http://localhost:8080/api/system/stop", null).subscribe({
      next: (response) => {
        // Update the system state after the stop request
        this.masterService.updateSystemState();
        this.buttonLabel = 'Resume';
        this.toaster.info('Simulation Stopped');
        console.log('System stopped');
      },
      error: (e) => {
        console.error('System encountered an error while stopping', e);
      }
    });
  }

  /**
   * Resets the simulation and updates system state and users.
   * Resets vendors, customers, and priority customers, and changes the button label to 'Start'.
   * Displays a warning message to indicate the reset action.
   */
  reset() {
    this.http.post("http://localhost:8080/api/system/reset", null).subscribe({
      next: (response) => {
        // Update system state and reset users
        this.masterService.updateSystemState();
        this.masterService.updateVendors();
        this.masterService.updateCustomers();
        this.masterService.updatePriorityCustomers();
        this.buttonLabel = 'Start';
        this.toaster.warning('Simulation Reset');
        console.log('System reset');
      },
      error: (e) => {

        console.error('System encountered an error while resetting', e);
      }
    });
  }

  // Exposing SystemState enum for use in the template
  protected readonly SystemState = SystemState;
}
