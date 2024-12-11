import {Component, inject, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {Button} from 'primeng/button';
import {HttpClient} from '@angular/common/http';
import {IConfiguration} from '../../model/class/configuration';
import {NgClass, NgStyle} from '@angular/common';
import {MasterControlService} from '../../services/master-control/master-control.service';
import {SystemState} from '../../model/class/state';
import {ToastrService} from 'ngx-toastr';


@Component({
  selector: 'app-configuration-form',
  standalone: true,
  imports: [
    FormsModule,
    InputTextModule,
    Button,
    NgClass,
    NgStyle,

  ],

  templateUrl: './configuration-form.component.html',
  styleUrl: './configuration-form.component.css'
})
export class ConfigurationFormComponent implements OnInit{

  configuration: IConfiguration = {
    totalTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maxTicketCapacity: 0,
  }

  systemState: SystemState = SystemState.STOPPED;

  http = inject(HttpClient);
  masterService = inject(MasterControlService);
  toaster = inject(ToastrService);

  ngOnInit() {
    this.masterService.state$.subscribe((state) => {
      this.systemState = state;
    });
    this.masterService.updateSystemState();
    this.loadConfiguration();
  }

  loadConfiguration() {
    if (this.systemState === SystemState.STOPPED) {
      this.http.get<IConfiguration>("http://localhost:8080/api/configuration/load-configuration").subscribe((config:IConfiguration)=>{
        if (config!=null){
          this.configuration = config;
          this.toaster.success('Configuration Loaded')
        }
      })
    }
  }

  saveConfiguration() {
    this.http.post("http://localhost:8080/api/configuration/save-configuration", this.configuration)
      .subscribe();
    this.toaster.success('Configuration Saved')
  }

  validateTotalTickets(): string {

    if (this.configuration.totalTickets <= 0) {
      return 'Total Tickets has to be greater than 0';
    }
    else if (this.configuration.totalTickets > this.configuration.maxTicketCapacity) {
      return 'Total Tickets has to be less than Max Ticket Capacity';
    }
    else {
      return 'Enter total tickets at the start of the simulation';
    }
  }

  validateTicketReleaseRate(): string {
    if (this.configuration.ticketReleaseRate <= 0) {
      return 'Ticket Release Rate has to be greater than 0';
    }
    else if (this.configuration.ticketReleaseRate >= this.configuration.maxTicketCapacity) {
      return 'Ticket Release Rate has to be lower than Max Ticket Capacity';
    }
    else {
      return 'Enter number of tickets added per second';
    }
  }

  validateCustomerRetrievalRate(): string {
    if (this.configuration.customerRetrievalRate <= 0) {
      return 'Customer Retrieval Rate has to be greater than 0';
    }
    else if (this.configuration.customerRetrievalRate >= this.configuration.maxTicketCapacity) {
      return 'Customer Retrieval Rate has to be lower than Max Ticket Capacity';
    }
    else {
      return 'Enter number of tickets purchased per second';
    }
  }

  validateMaxTicketCapacity(): string {

    if (this.configuration.maxTicketCapacity <= 0) {
      return 'Max Ticket Capacity has to be greater than 0';
    }
    else if (this.configuration.totalTickets > this.configuration.maxTicketCapacity) {
      return 'Max Ticket Capacity cannot be less than Total Tickets';
    }
    else {
      return 'Enter maximum tickets that can be in the ticketpool at a given time';
    }
  }

  validateSaveButtonAvailability () : boolean {
    return (
      this.validateTotalTickets() === 'Enter total tickets at the start of the simulation' &&
      this.validateTicketReleaseRate() === 'Enter number of tickets added per second' &&
      this.validateCustomerRetrievalRate() === 'Enter number of tickets purchased per second' &&
      this.validateMaxTicketCapacity() === 'Enter maximum tickets that can be in the ticketpool at a given time'
    );
  }

}
