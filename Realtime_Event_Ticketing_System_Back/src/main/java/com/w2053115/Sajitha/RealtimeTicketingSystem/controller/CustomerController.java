package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "/add-customer")
    public void addCustomer(){
        customerService.createCustomer(false);
    }

    @DeleteMapping(path = "/remove-customer")
    public void removeCustomer(){
        customerService.removeCustomer(false);
    }

    @GetMapping(path = "/get-customer")
    public int getCustomer() {
        return customerService.getCustomers();
    }



    @PostMapping(path = "/add-priority-customer")
    public void addPriorityCustomer(){
        customerService.createCustomer(true);
    }

    @DeleteMapping(path = "/remove-priority-customer")
    public void removePriorityCustomer(){
        customerService.removeCustomer(true);
    }

    @GetMapping(path = "/get-priority-customer")
    public int getPriorityCustomer() {
        return customerService.getPriorityCustomers();
    }
}
