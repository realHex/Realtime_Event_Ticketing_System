package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.SystemState;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.TicketPool;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.CustomerService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.SystemService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemServiceImpl implements SystemService {

    private static final Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

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
            ticketPool.initializeTicketpool(configuration.getTotalTickets(), configuration.getMaxTicketCapacity());
        }
        catch(Exception e) {
            logger.error("Error while initializing");
            throw e;
        }
        //initialize a ticketpool
        //make ticketpool
        //reset and display no of vendors and customers
    }

    @Override
    public String startApplication() {
        if (vendorService.getVendors() <= 0 && customerService.getCustomers() <=0) {
            logger.warn("Add some vendors or customers before starting");
            return "Add some vendors or customers before starting";
        }
        if (SystemState.getState()==SystemState.RUNNING) {
            logger.info("Application already running");
            return "Application already running";
        } else if (SystemState.getState()==SystemState.PAUSED){
            vendorService.resumeVendors();
            customerService.resumeCustomers();
            SystemState.setState(SystemState.RUNNING);
            logger.info("Application resumed");
            return "Application resumed";
        }
        else {
            vendorService.startVendors();
            customerService.startCustomers();
            SystemState.setState(SystemState.RUNNING);
            logger.info("Application started");
            return "Application started";
        }
    }

    @Override
    public String stopApplication() {
        if (SystemState.getState()==SystemState.RUNNING) {
            vendorService.stopVendors();
            customerService.stopCustomers();
            SystemState.setState(SystemState.PAUSED);
            logger.info("Application stopped");
            return "Application stopped";
        } else {
            logger.info("Application is not running");
            return "Application is not running";
        }
    }

    @Override
    public String resetApplication() {
        if (SystemState.getState()==SystemState.RUNNING) {
            logger.info("Stop the application before resetting");
            return "Stop the application before resetting";
        }
        else {
            ticketPool.resetTicketpool();
            vendorService.resetVendors();
            customerService.resetCustomers();
            SystemState.setState(SystemState.STOPPED);
            initializer();
            logger.info("Application reset");
            return "Application reset";
        }
    }

}
