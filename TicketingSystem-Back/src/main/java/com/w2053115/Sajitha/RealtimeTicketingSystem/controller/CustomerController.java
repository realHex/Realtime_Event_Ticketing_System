package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling HTTP requests related to customers in the ticketing system.
 * It provides endpoints for adding, removing, and retrieving both regular and priority customers.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from the Angular frontend
@RequestMapping("api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Endpoint to add a regular customer to the system.
     */
    @PostMapping(path = "/add-customer")
    public void addCustomer() {
        customerService.createCustomer(false);
    }

    /**
     * Endpoint to remove a regular customer from the system.
     */
    @DeleteMapping(path = "/remove-customer")
    public void removeCustomer() {
        customerService.removeCustomer(false);
    }

    /**
     * Endpoint to retrieve the total number of regular customers.
     *
     * @return The current number of regular customers.
     */
    @GetMapping(path = "/get-customer")
    public int getCustomer() {
        return customerService.getCustomers();
    }

    /**
     * Endpoint to add a priority customer to the system.
     */
    @PostMapping(path = "/add-priority-customer")
    public void addPriorityCustomer() {
        customerService.createCustomer(true);
    }

    /**
     * Endpoint to remove a priority customer from the system.
     */
    @DeleteMapping(path = "/remove-priority-customer")
    public void removePriorityCustomer() {
        customerService.removeCustomer(true);
    }

    /**
     * Endpoint to retrieve the total number of priority customers.
     *
     * @return The current number of priority customers.
     */
    @GetMapping(path = "/get-priority-customer")
    public int getPriorityCustomer() {
        return customerService.getPriorityCustomers();
    }
}
