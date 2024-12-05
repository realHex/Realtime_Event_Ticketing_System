package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:27017")
@RequestMapping("api/vendor")
public class VendorController {
    @PostMapping(path = "/add-vendor")
    public String addVendor(){

    }
}
