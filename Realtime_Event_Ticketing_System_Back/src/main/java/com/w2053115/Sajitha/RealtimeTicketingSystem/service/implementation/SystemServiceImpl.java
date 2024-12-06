package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.SystemState;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.TicketPool;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.CustomerService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.SystemService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.VendorService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    Configuration configuration;

    @Autowired
    TicketPool ticketPool;

    @Autowired
    VendorService vendorService;

    @Autowired
    CustomerService customerService;

    @Override
    public void initializer() {
        try {
            System.out.println(configuration.getTotalTickets());
            System.out.println(configuration.getMaxTicketCapacity());
            ticketPool.initializeTicketpool(configuration.getTotalTickets(), configuration.getMaxTicketCapacity());
        }
        catch(Exception e) {
            System.out.println("Error while initializing");
            throw e;
        }
        //load config
        //initialize a ticketpool
        //make ticketpool
        //reset and display no of vendors and customers
    }

    @Override
    public String startApplication() {
        if (vendorService.getVendors() <= 0) {
            System.out.println("Not enough vendors");
            return "Not enough vendors";
        } else if (customerService.getCustomers() <= 0) {
            System.out.println("Not enough customers");
            return "Not enough customers";
        } else if (SystemState.getState()==SystemState.RUNNING) {
            System.out.println("Application already running");
            return "Application already running";
        } else {
            vendorService.startVendors();
            customerService.startCustomers();
            SystemState.setState(SystemState.RUNNING);
            System.out.println("Application started");
            return "Application started";
        }
    }

    @Override
    public String stopApplication() {
        if (SystemState.getState()==SystemState.RUNNING) {
            vendorService.stopVendors();
            customerService.stopCustomers();
            SystemState.setState(SystemState.STOPPED);
            System.out.println("Application is not running");
            return "Application is not running";
        } else {
            System.out.println("Application stopped");
            return "Application stopped";
        }
    }

    @Override
    public String resetApplication() {
        if (SystemState.getState()==SystemState.RUNNING) {
            System.out.println("Stop the application before resetting");
            return "Stop the application before resetting";
        }
        else {
            ticketPool.resetTicketpool();
            vendorService.resetVendors();
            customerService.resetCustomers();
            System.out.println("Application reset");
            return "Application reset";
        }
    }

}
