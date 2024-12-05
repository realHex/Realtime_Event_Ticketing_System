package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Vendor;
import com.w2053115.Sajitha.RealtimeTicketingSystem.repositary.VendorRepo;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.ConfigurationService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.TicketPool;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.SystemService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.VendorService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.runnables.VendorRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VendorServiceImpl implements VendorService {

    private final ArrayList<Thread> vendorThreadList = new ArrayList<>();
    private final ArrayList<VendorRunner> vendorObjectList = new ArrayList<>();

    @Autowired
    private VendorRepo vendorRepo;

    @Autowired
    ConfigurationService configurationService;

    @Autowired
    private TicketPool ticketPool;

    @Autowired
    private SystemService systemService;

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
        VendorRunner vendorObject = new VendorRunner(
                configuration.getTicketReleaseRate(),
                1,
                ticketPool
        );
        vendorObjectList.add(vendorObject);

        //Creating vendor threads and storing it for accessing
        Thread vendorThread = new Thread(vendorObject);
        vendorThreadList.add(vendorThread);
        if (systemService.getStart()) {
            vendorThread.start();
        }
        for (Thread x : vendorThreadList) {
            System.out.println(x.getName());
        }
        return "Vendor " + vendorObject.getVendorId() + " created successfully";
    }

    public String removeVendor(){
        try {
            if (vendorRepo!=null && !vendorObjectList.isEmpty() && !vendorThreadList.isEmpty()) {
                int vendorId = vendorObjectList.getLast().getVendorId();

                vendorRepo.delete(vendorRepo.findFirstByOrderByCreatedDesc());

                //Delete vendor object and thread
                vendorObjectList.removeLast();
                vendorThreadList.removeLast();


                System.out.println("Vendor " + vendorId + " Removed");
                for (Thread x : vendorThreadList) {
                    System.out.println(x.getName());
                }
                return "Vendor " + vendorId + " Removed";
            }
            else {
                System.out.println("Vendor objects are null");
                return "Vendor objects are null";
            }
        }
        catch (NullPointerException e) {
            System.out.println("No records for vendor");
            throw e;
        }
    }

    public void startVendors(){
        for (Thread thread : vendorThreadList) {
            thread.start();
        }
    }

    public void stopVendors(){
        try {
            for (Thread thread : vendorThreadList) {
                thread.wait();
            }
        } catch (InterruptedException e) {
            System.out.println("Error while stopping vendor threads");
        }
    }

}
