package com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces;

import org.springframework.stereotype.Service;

/**
 * Interface defining the methods for managing customer operations in the ticketing system.
 * It includes methods for creating, removing, starting, stopping, and resetting customers,
 * as well as retrieving the count of customers and priority customers.
 */
@Service
public interface CustomerService {

    /**
     * Creates a new customer with the specified priority status.
     *
     * @param priority true if the customer is a priority customer, false otherwise
     */
    void createCustomer(boolean priority);

    /**
     * Removes an existing customer based on their priority status.
     *
     * @param priority true if the customer is a priority customer, false otherwise
     */
    void removeCustomer(boolean priority);

    /**
     * Starts all customer threads, enabling them to begin retrieving tickets.
     */
    void startCustomers();

    /**
     * Stops all customer threads, pausing their operations.
     */
    void stopCustomers();

    /**
     * Resumes all customer threads, allowing them to continue retrieving tickets.
     */
    void resumeCustomers();

    /**
     * Resets all customer threads, including clearing the customer count.
     */
    void resetCustomers();

    /**
     * Retrieves the total number of regular customers.
     *
     * @return the total number of regular customers
     */
    int getCustomers();

    /**
     * Retrieves the total number of priority customers.
     *
     * @return the total number of priority customers
     */
    int getPriorityCustomers();
}
