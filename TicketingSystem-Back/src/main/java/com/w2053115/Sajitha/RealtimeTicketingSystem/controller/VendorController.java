package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping(path = "/add-vendor")
    public void addVendor(){
        vendorService.createVendor();
    }

    @DeleteMapping(path = "/remove-vendor")
    public void removeVendor(){
        vendorService.removeVendor();
    }

    @GetMapping(path = "/get-vendor")
    public int getVendor() {
        return vendorService.getVendors();
    }
}
