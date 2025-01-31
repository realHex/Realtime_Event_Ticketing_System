package org.w2053115.Setups.Users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w2053115.Setups.AbstractTicketHandler;
import org.w2053115.Setups.SharedTicketPool.TicketPool;

/**
 * This class represents a customer who interacts with a shared ticket pool.
 *
 * Customers are responsible for retrieving tickets from the pool at a
 * predefined interval and quantity. They inherit from the `AbstractTicketHandler`
 * class to provide a common base for handling ticket operations.
 */
public class Customer extends AbstractTicketHandler implements Runnable {

    private final int customerId;
    private final int ticketsPerRetrieval;
    private final int retrievalInterval;
    private final int priority;

    static int customerNo = 1;

    static boolean running = false;

    private static final Logger logger = LogManager.getLogger(Customer.class);

    /**
     * Constructs a new Customer object.
     *
     * @param ticketPool The shared ticket pool used by the customer.
     * @param ticketsPerRelease The number of tickets retrieved per interval.
     * @param releaseInterval The interval (in seconds) between ticket retrievals.
     * @param priority The priority level of the customer (used for potential future enhancements).
     */
    public Customer(TicketPool ticketPool, int ticketsPerRelease, int releaseInterval, int priority) {
        super(ticketPool);
        this.customerId = customerNo++;
        this.ticketsPerRetrieval = ticketsPerRelease;
        this.retrievalInterval = releaseInterval;
        this.priority = priority;
    }

    /**
     * Implements the `run()` method from the `Runnable` interface.
     *
     * This method defines the customer's behavior in a separate thread. It continuously
     * attempts to remove tickets from the pool at the specified interval until interrupted.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ticketPool.removeTicket(customerId, ticketsPerRetrieval);
                Thread.sleep(retrievalInterval * 1000L); // waiting after removing a ticket
            }
        } catch (InterruptedException e) {
            if (running) {
                logger.error("Customer {} has been interrupted", customerId);
            } else {
                logger.info("Customer {} has been stopped", customerId);
            }
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Sets the `running` flag to true, indicating that customer threads are running.
     */
    public static void setRunningTrue() {
        running = true;
    }

    /**
     * Sets the `running` flag to false, indicating that customer threads are stopped.
     */
    public static void setRunningFalse() {
        running = false;
    }

    /**
     * Resets the `customerNo` counter to 1 for assigning unique IDs.
     */
    public static void resetCustomerNo() {
        customerNo = 1;
    }
}