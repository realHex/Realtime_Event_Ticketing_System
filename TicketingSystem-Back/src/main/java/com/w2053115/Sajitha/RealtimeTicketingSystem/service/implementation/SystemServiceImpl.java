package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.TransactionLogsService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.SystemState;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.TicketPool;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.CustomerService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.SystemService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the SystemService interface.
 * Provides core functionalities for initializing, starting, stopping, and resetting the application.
 */
@Service
public class SystemServiceImpl implements SystemService {

    private static final Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

    // Injected dependencies
    @Autowired
    Configuration configuration;

    @Autowired
    TicketPool ticketPool;

    @Autowired
    VendorService vendorService;

    @Autowired
    CustomerService customerService;

    @Autowired
    TransactionLogsService transactionLogsService;

    /**
     * Initializes the application by setting up the ticket pool with configuration parameters.
     */
    @Override
    public void initializer() {
        try {
            // Initialize the ticket pool
            ticketPool.initializeTicketpool(configuration.getTotalTickets(), configuration.getMaxTicketCapacity());
        } catch (Exception e) {
            logger.error("Error while initializing");
            throw e;
        }
    }

    /**
     * Starts the application by initializing vendors and customers and changing the system state to RUNNING.
     * Resumes operation if the application is in a PAUSED state.
     *
     * @return A message indicating the result of the operation.
     */
    @Override
    public String startApplication() {
        // Ensure there are vendors or customers before starting.
        if (vendorService.getVendors() <= 0 && customerService.getCustomers() <= 0) {
            logger.warn("Add some vendors or customers before starting");
            return "Add some vendors or customers before starting";
        }

        // Check the current state of the application.
        if (SystemState.getState() == SystemState.RUNNING) {
            // If already running, log and return a message.
            logger.info("Application already running");
            return "Application already running";
        } else if (SystemState.getState() == SystemState.PAUSED) {
            // Resume vendors and customers if the application is paused.
            vendorService.resumeVendors();
            customerService.resumeCustomers();
            SystemState.setState(SystemState.RUNNING); // Change state to RUNNING.
            logger.info("Application resumed");
            return "Application resumed";
        } else {
            // Start vendors and customers if the application is stopped.
            vendorService.startVendors();
            customerService.startCustomers();
            SystemState.setState(SystemState.RUNNING); // Change state to RUNNING.
            logger.info("Application started");
            return "Application started";
        }
    }

    /**
     * Stops the application by stopping vendors and customers and changing the system state to PAUSED.
     *
     * @return A message indicating the result of the operation.
     */
    @Override
    public String stopApplication() {
        // Check if the application is currently running.
        if (SystemState.getState() == SystemState.RUNNING) {
            // Stop all vendors and customers.
            vendorService.stopVendors();
            customerService.stopCustomers();
            SystemState.setState(SystemState.PAUSED); // Change state to PAUSED.
            logger.info("Application stopped");
            return "Application stopped";
        } else {
            // If not running, log and return a message.
            logger.info("Application is not running");
            return "Application is not running";
        }
    }

    /**
     * Resets the application by clearing the ticket pool, resetting vendors, customers, and logs,
     * and reinitializing the application. Ensures the application is stopped before resetting.
     *
     * @return A message indicating the result of the operation.
     */
    @Override
    public String resetApplication() {
        // Check if the application is running.
        if (SystemState.getState() == SystemState.RUNNING) {
            // Log and return a message if the application is running.
            logger.info("Stop the application before resetting");
            return "Stop the application before resetting";
        } else {
            // Clear the ticket pool.
            ticketPool.resetTicketpool();
            // Reset vendors and customers.
            vendorService.resetVendors();
            customerService.resetCustomers();
            // Clear transaction logs.
            transactionLogsService.resetLogs();

            // Set the system state to STOPPED.
            SystemState.setState(SystemState.STOPPED);
            // Reinitialize the application.
            initializer();
            logger.info("Application reset");
            return "Application reset";
        }
    }
}
