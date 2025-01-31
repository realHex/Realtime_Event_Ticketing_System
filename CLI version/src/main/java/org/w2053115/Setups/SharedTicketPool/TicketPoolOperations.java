package org.w2053115.Setups.SharedTicketPool;

/**
 * This interface defines operations for managing a shared ticket pool.
 *
 * The ticket pool is a common resource that can be accessed by multiple threads
 * to obtain and release tickets.
 */
public interface TicketPoolOperations {

    /**
     * Adds a specified number of tickets to the pool on behalf of a given vendor.
     *
     * @param vendorId The ID of the vendor adding the tickets.
     * @param noOfTickets The number of tickets to add.
     */
    void addTicket(int vendorId, int noOfTickets);

    /**
     * Removes a specified number of tickets from the pool on behalf of a given customer.
     *
     * @param customerId The ID of the customer removing the tickets.
     * @param noOfTickets The number of tickets to remove.
     */
    void removeTicket(int customerId, int noOfTickets);

    /**
     * Populates the ticket pool with initial tickets before thread execution begins.
     *
     * This method is responsible for initializing the ticket pool with a starting
     * number of tickets.
     */
    void ticketPoolPopulator();
}