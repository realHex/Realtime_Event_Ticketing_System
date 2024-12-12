package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling HTTP requests related to vendors.
 * It provides endpoints to manage vendors such as adding, removing, and retrieving the number of vendors.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from the Angular frontend
@RequestMapping("api/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    /**
     * Endpoint to add a vendor.
     * Calls the vendor service to create a new vendor.
     */
    @PostMapping(path = "/add-vendor")
    public void addVendor(){
        vendorService.createVendor();
    }

    /**
     * Endpoint to remove a vendor.
     * Calls the vendor service to remove an existing vendor.
     */
    @DeleteMapping(path = "/remove-vendor")
    public void removeVendor(){
        vendorService.removeVendor();
    }

    /**
     * Endpoint to retrieve the number of vendors currently available.
     * Returns the count of vendors by calling the vendor service.
     *
     * @return The current number of vendors.
     */
    @GetMapping(path = "/get-vendor")
    public int getVendor() {
        return vendorService.getVendors();
    }
}
