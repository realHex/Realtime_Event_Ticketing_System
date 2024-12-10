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
    public String addCustomer(){
        return customerService.createCustomer();
    }

    @DeleteMapping(path = "/remove-customer")
    public String removeCustomer(){
        return customerService.removeCustomer();
    }

    @GetMapping(path = "/get-customer")
    public int getCustomer() {
        return customerService.getCustomers();
    }
}
