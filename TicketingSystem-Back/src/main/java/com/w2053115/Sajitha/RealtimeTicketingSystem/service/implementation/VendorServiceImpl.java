package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Vendor;
import com.w2053115.Sajitha.RealtimeTicketingSystem.repository.VendorRepo;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.SystemState;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.TicketPool;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.VendorService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.runnable.VendorRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;



@Service
public class VendorServiceImpl implements VendorService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);

    //Arrays to store vendor customer objects and threads
    private final ArrayList<Thread> vendorThreadList = new ArrayList<>();
    private final ArrayList<VendorRunner> vendorObjectList = new ArrayList<>();
    //Storing number of vendors
    private int noOfVendors = 0;

    @Autowired
    private VendorRepo vendorRepo;

    @Autowired
    private TicketPool ticketPool;

    @Autowired
    Configuration configuration;

    @Override
    public void createVendor() {
        //Checking if a configuration is loaded before adding customers
        if (!isConfigurationLoaded()) {
            logger.warn("Load a Configuration before adding vendors");
            return;
        }

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

        //Starting the created thread if the system is already running/paused
        if (SystemState.getState()==SystemState.RUNNING || SystemState.getState()==SystemState.PAUSED) {
            vendorThread.start();
        }
        noOfVendors++;
        logger.info("Vendor {} created successfully", vendorObject.getVendorId());

    }

    @Override
    public void removeVendor(){
        try {
            //Checking vendor type and if there actually are customers
            if (!vendorObjectList.isEmpty() && !vendorThreadList.isEmpty()) {
                //Getting id to display it in logs
                int vendorId = vendorObjectList.getLast().getVendorId();

                vendorRepo.delete(vendorRepo.findFirstByOrderByCreatedDesc());

                //Delete vendor object and thread
                vendorThreadList.getLast().interrupt();
                vendorObjectList.removeLast();
                vendorThreadList.removeLast();

                noOfVendors--;
                VendorRunner.reduceId();  //Reducing priority customer id in runnable class
                logger.info("Vendor {} Removed", vendorId);
            }
            else {
                logger.warn("There's no vendors added");
            }
        }
        catch (NullPointerException e) {
            logger.error("No records for vendor");
            throw e;
        }
    }

    @Override
    public void startVendors(){
        //Iterating through thread lists and starting each thread
        for (Thread thread : vendorThreadList) {
            thread.start();
        }

        logger.info("Vendors started");
    }

    @Override
    public void stopVendors(){
        //Changing static variable 'paused' in Runnable class to true
        VendorRunner.stop();
        logger.info("Vendors stopped");
    }

    @Override
    public void resumeVendors(){
        //Changing static variable 'paused' in Runnable class to false
        VendorRunner.resume();
    }

    @Override
    public void resetVendors(){
        //Checking if vendor lists are not empty
        if (!vendorObjectList.isEmpty() && !vendorThreadList.isEmpty()){
            for (Thread thread : vendorThreadList) {
                thread.interrupt(); //Iterating and stopping all threads
            }
            //Clearing both object and thread lists
            vendorObjectList.clear();
            vendorThreadList.clear();
        }
        VendorRunner.resume(); //Changing 'pause' variable to false
        noOfVendors = 0;
        VendorRunner.resetVendorIds();
        logger.info("Vendors reset");
    }

    @Override
    public int getVendors() {
        return noOfVendors;
    }

    private boolean isConfigurationLoaded() {
        return (configuration.getMaxTicketCapacity() > 0 &&
                configuration.getTotalTickets() > 0 &&
                configuration.getTicketReleaseRate() > 0 &&
                configuration.getCustomerRetrievalRate() > 0);
    }

}
