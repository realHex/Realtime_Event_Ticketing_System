package com.w2053115.Sajitha.RealtimeTicketingSystem.Controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.Model.Vendor;
import com.w2053115.Sajitha.RealtimeTicketingSystem.Repositary.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //To state that we're defining API and path
public class MainController {
    @Autowired //Since we're accessing an interface but creating an object in some other file
    VendorRepo  vendorRepo;
    @PostMapping("/addVendor") //Receives data sent from postman and inserts it into MongoDB database
    public void addVendor(@RequestBody Vendor vendor){
        vendorRepo.save(vendor);
    }
}
