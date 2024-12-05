package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Vendor;
import com.w2053115.Sajitha.RealtimeTicketingSystem.repositary.VendorRepo;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.ConfigurationService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.SystemService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.TicketPool;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.VendorService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.runnables.VendorRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VendorServiceImpl implements VendorService {

    private final ArrayList<Thread> vendorThreadList = new ArrayList<>();

    @Autowired
    private VendorRepo vendorRepo;

    @Autowired
    ConfigurationService configurationService;

    @Autowired
    private TicketPool ticketPool;

    @Autowired
    private SystemServiceImpl systemService;

    @Override
    public String createVendor() {
        //Load the configuration parameters into an object
        Configuration configuration = configurationService.loadConfiguration();

        //Add vendor details to the database
        Vendor vendor = new Vendor(
                configuration.getTicketReleaseRate(),
                1,
                "John Smith",
                "0774950215"
        );
        vendorRepo.save(vendor);

        //Create vendor object (for making the thread)
        VendorRunService vendorObject = new VendorRunService(
                configuration.getTicketReleaseRate(),
                1,
                ticketPool
        );

        //Creating vendor threads and storing it for accessing
        Thread vendorThread = new Thread(vendorObject);
        vendorThreadList.add(vendorThread);
        if (systemService.getStart()) {
            vendorThread.start();
        }
        return "Vendor " + vendorObject.getVendorId() + " created successfully";
    }
}
