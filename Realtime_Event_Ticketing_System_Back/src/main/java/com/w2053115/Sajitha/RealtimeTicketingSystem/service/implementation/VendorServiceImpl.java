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
    public void createVendor() {

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
        if (SystemState.getState()==SystemState.RUNNING || SystemState.getState()==SystemState.PAUSED) {
            vendorThread.start();
        }
        noOfVendors++;
        logger.info("Vendor {} created successfully", vendorObject.getVendorId());

    }

    @Override
    public void removeVendor(){
        try {
            if (vendorRepo!=null && !vendorObjectList.isEmpty() && !vendorThreadList.isEmpty()) {
                int vendorId = vendorObjectList.getLast().getVendorId();

                vendorRepo.delete(vendorRepo.findFirstByOrderByCreatedDesc());

                //Delete vendor object and thread
                vendorThreadList.getLast().interrupt();
                vendorObjectList.removeLast();
                vendorThreadList.removeLast();

                noOfVendors--;
                VendorRunner.reduceId();
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
        for (Thread thread : vendorThreadList) {
            thread.start();
        }
        logger.info("Vendors started");
    }

    @Override
    public void stopVendors(){
        VendorRunner.stop();
        logger.info("Vendors stopped");
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
