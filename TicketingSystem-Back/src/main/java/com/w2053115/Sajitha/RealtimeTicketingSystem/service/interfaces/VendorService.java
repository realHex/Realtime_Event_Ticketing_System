package com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces;

import org.springframework.stereotype.Service;

/**
 * Interface for managing vendor operations in the ticketing system.
 * It provides methods for creating, removing, starting, stopping,
 * and resuming vendor activities, as well as retrieving the count of vendors.
 */
@Service
public interface VendorService {

    /**
     * Creates a new vendor in the ticketing system.
     */
    void createVendor();

    /**
     * Removes an existing vendor from the ticketing system.
     */
    void removeVendor();

    /**
     * Starts all vendor processes (e.g., releasing tickets).
     */
    void startVendors();

    /**
     * Stops all vendor processes.
     */
    void stopVendors();

    /**
     * Resumes all vendor processes that were previously stopped.
     */
    void resumeVendors();

    /**
     * Resets all vendor-related information (e.g., vendor IDs).
     */
    void resetVendors();

    /**
     * Retrieves the current number of active vendors.
     *
     * @return the count of active vendors
     */
    int getVendors();
}
