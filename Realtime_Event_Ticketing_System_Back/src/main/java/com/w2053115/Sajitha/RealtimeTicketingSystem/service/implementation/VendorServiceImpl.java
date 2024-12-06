package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Vendor;
import com.w2053115.Sajitha.RealtimeTicketingSystem.repository.VendorRepo;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.SystemState;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.TicketPool;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.VendorService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.runnable.VendorRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class VendorServiceImpl implements VendorService {

    private final ArrayList<Thread> vendorThreadList = new ArrayList<>();
    private final ArrayList<VendorRunner> vendorObjectList = new ArrayList<>();
    private int noOfVendors = 0;

    @Autowired
    private VendorRepo vendorRepo;

    @Autowired
    private TicketPool ticketPool;

    @Autowired
    Configuration configuration;

    @Override
    public String createVendor() {

        //Add vendor details to the database
        Vendor vendor = new Vendor(
                configuration.getTicketReleaseRate(),
                1,
                "John Smith",
                "0774950215"
        );
        vendorRepo.save(vendor);
        System.out.println(configuration.getTotalTickets());
        System.out.println(configuration.getTicketReleaseRate());
        System.out.println(configuration.getCustomerRetrievalRate());
        System.out.println(configuration.getMaxTicketCapacity());


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
        if (SystemState.getState()==SystemState.RUNNING) {
            vendorThread.start();
        }
        for (Thread x : vendorThreadList) {
            System.out.println(x.getName());
        }
        noOfVendors++;
        return "Vendor " + vendorObject.getVendorId() + " created successfully";
    }

    @Override
    public String removeVendor(){
        try {
            if (vendorRepo!=null && !vendorObjectList.isEmpty() && !vendorThreadList.isEmpty()) {
                int vendorId = vendorObjectList.getLast().getVendorId();

                vendorRepo.delete(vendorRepo.findFirstByOrderByCreatedDesc());

                //Delete vendor object and thread
                vendorThreadList.getLast().interrupt();
                vendorObjectList.removeLast();
                vendorThreadList.removeLast();


                System.out.println("Vendor " + vendorId + " Removed");
                for (Thread x : vendorThreadList) {
                    System.out.println(x.getName());
                }
                noOfVendors--;
                VendorRunner.reduceId();
                return "Vendor " + vendorId + " Removed";
            }
            else {
                System.out.println("There's no vendors added");
                return "Vendor objects are null";
            }
        }
        catch (NullPointerException e) {
            System.out.println("No records for vendor");
            throw e;
        }
    }

    @Override
    public void startVendors(){
        if (noOfVendors < 0) {
            System.out.println("There are no vendors");
            return;
        }
        for (Thread thread : vendorThreadList) {
            thread.start();
        }
        System.out.println("Vendors started");
    }

    @Override
    public void stopVendors(){
        VendorRunner.stop();
        System.out.println("Vendors stopped");
    }

    @Override
    public void resumeVendors(){
        VendorRunner.resume();
    }

    @Override
    public void resetVendors(){
        if (!vendorObjectList.isEmpty() && !vendorThreadList.isEmpty()){
            for (Thread thread : vendorThreadList) {
                thread.interrupt();
            }
            //Collections.fill(vendorObjectList, null);
            vendorObjectList.clear();
            vendorThreadList.clear();
        }
        VendorRunner.resume();
        noOfVendors = 0;
        VendorRunner.resetVendorIds();
        System.out.println("Vendors reset");
    }

    @Override
    public int getVendors() {
        return noOfVendors;
    }

}
