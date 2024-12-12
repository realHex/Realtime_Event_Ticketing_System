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


/**
 * Service implementation for managing vendors in the real-time ticketing system.
 * Handles creation, removal, and lifecycle operations for vendors.
 */

@Service
public class VendorServiceImpl implements VendorService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);

    // Arrays to store vendor threads and objects
    private final ArrayList<Thread> vendorThreadList = new ArrayList<>();
    private final ArrayList<VendorRunner> vendorObjectList = new ArrayList<>();
    private int noOfVendors = 0; // Stores the number of vendors

    @Autowired
    private VendorRepo vendorRepo;

    @Autowired
    private TicketPool ticketPool;

    @Autowired
    Configuration configuration;

    /**
     * Creates a new vendor by adding vendor details to the database,
     * creating a vendor runner object, and starting its thread if the system is running or paused.
     */
    @Override
    public void createVendor() {
        if (!isConfigurationLoaded()) {
            logger.warn("Load a Configuration before adding vendors");
            return;
        }

        // Add vendor details to the database
        Vendor vendor = new Vendor(
                configuration.getTicketReleaseRate(),
                1,
                "John Smith",
                "0774950215"
        );
        vendorRepo.save(vendor);

        // Create a VendorRunner object and corresponding thread
        VendorRunner vendorObject = new VendorRunner(
                configuration.getTicketReleaseRate(),
                1,
                ticketPool
        );
        vendorObjectList.add(vendorObject);

        Thread vendorThread = new Thread(vendorObject);
        vendorThreadList.add(vendorThread);

        // Start the thread if the system state is RUNNING or PAUSED
        if (SystemState.getState() == SystemState.RUNNING || SystemState.getState() == SystemState.PAUSED) {
            vendorThread.start();
        }
        noOfVendors++;
        logger.info("Vendor {} created successfully", vendorObject.getVendorId());
    }

    /**
     * Removes the most recently added vendor by interrupting its thread,
     * removing its corresponding object, and deleting its record from the database.
     */
    @Override
    public void removeVendor() {
        try {
            if (!vendorObjectList.isEmpty() && !vendorThreadList.isEmpty()) {
                int vendorId = vendorObjectList.get(vendorObjectList.size() - 1).getVendorId();

                vendorRepo.delete(vendorRepo.findFirstByOrderByCreatedDesc());

                // Remove vendor thread and object
                vendorThreadList.get(vendorThreadList.size() - 1).interrupt();
                vendorObjectList.remove(vendorObjectList.size() - 1);
                vendorThreadList.remove(vendorThreadList.size() - 1);

                noOfVendors--;
                VendorRunner.reduceId();
                logger.info("Vendor {} Removed", vendorId);
            } else {
                logger.warn("There's no vendors added");
            }
        } catch (NullPointerException e) {
            logger.error("No records for vendor");
            throw e;
        }
    }

    /**
     * Starts all vendor threads by iterating through the thread list and starting each thread.
     */
    @Override
    public void startVendors() {
        for (Thread thread : vendorThreadList) {
            thread.start();
        }
        logger.info("Vendors started");
    }

    /**
     * Stops all vendor threads by changing the `paused` flag in the VendorRunner class to true.
     */
    @Override
    public void stopVendors() {
        VendorRunner.stop();
        logger.info("Vendors stopped");
    }

    /**
     * Resumes all vendor threads by changing the `paused` flag in the VendorRunner class to false.
     */
    @Override
    public void resumeVendors() {
        VendorRunner.resume();
    }

    /**
     * Resets all vendors by interrupting their threads, clearing the thread and object lists,
     * and resetting the vendor IDs to their initial state.
     */
    @Override
    public void resetVendors() {
        if (!vendorObjectList.isEmpty() && !vendorThreadList.isEmpty()) {
            for (Thread thread : vendorThreadList) {
                thread.interrupt(); // Stop all threads
            }
            vendorObjectList.clear(); // Clear the vendor object list
            vendorThreadList.clear(); // Clear the vendor thread list
        }
        VendorRunner.resume(); // Reset the paused state
        noOfVendors = 0;
        VendorRunner.resetVendorIds(); // Reset vendor IDs
        logger.info("Vendors reset");
    }

    /**
     * Returns the total number of vendors currently in the system.
     *
     * @return The number of vendors.
     */
    @Override
    public int getVendors() {
        return noOfVendors;
    }

    /**
     * Checks if a configuration is loaded by verifying that all necessary configuration parameters are greater than zero.
     *
     * @return True if the configuration is loaded, false otherwise.
     */
    private boolean isConfigurationLoaded() {
        return (configuration.getMaxTicketCapacity() > 0 &&
                configuration.getTotalTickets() > 0 &&
                configuration.getTicketReleaseRate() > 0 &&
                configuration.getCustomerRetrievalRate() > 0);
    }
}
