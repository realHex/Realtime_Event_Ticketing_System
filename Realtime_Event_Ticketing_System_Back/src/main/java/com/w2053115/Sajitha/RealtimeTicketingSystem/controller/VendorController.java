package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.VendorService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation.VendorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:27017")
@RequestMapping("api/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping(path = "/add-vendor")
    public String addVendor(){
        return vendorService.createVendor();
    }

    @PostMapping(path = "/remove-vendor")
    public String removeVendor(){
        return null;
    }
}
