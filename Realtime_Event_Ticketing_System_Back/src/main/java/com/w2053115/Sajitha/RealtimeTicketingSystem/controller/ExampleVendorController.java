package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.ExampleVendorModel;
import com.w2053115.Sajitha.RealtimeTicketingSystem.repositary.ExampleVendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //To state that we're defining API and path
public class ExampleVendorController {
    @Autowired //Since we're accessing an interface but creating an object in some other file
    ExampleVendorRepo exampleVendorRepo;
    @PostMapping("/addVendor") //Receives data sent from postman and inserts it into MongoDB database
    public void addVendor(@RequestBody ExampleVendorModel exampleVendorModel){
        exampleVendorRepo.save(exampleVendorModel);
    }
}
