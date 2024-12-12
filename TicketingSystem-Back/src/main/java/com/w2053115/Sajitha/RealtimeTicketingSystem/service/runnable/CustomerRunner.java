package com.w2053115.Sajitha.RealtimeTicketingSystem.service.runnable;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.TicketPool;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a customer interacting with the ticket pool in a real-time event ticketing system.
 * Implements the Runnable interface to allow for customer threads that continuously retrieve tickets
 * from the ticket pool at specified intervals.
 */
@Data
public class CustomerRunner implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CustomerRunner.class);

    private int customerId;
    private int priorityCustomerId;

    static int customerNo = 1;
    static int priorityCustomerNo = 1;

    private int ticketsPerRetrieval;
    private int retrievalInterval;
    private boolean priority;
    private final TicketPool ticketPool;
    private static volatile boolean paused = false;

    /**
     * Constructor to initialize a customer with ticket retrieval details and assign them a unique ID.
     *
     * @param ticketsPerRetrieval the number of tickets the customer retrieves per interval
     * @param releaseInterval the interval time (in seconds) between each ticket retrieval
     * @param priority indicates if the customer is a priority customer
     * @param ticketPool the shared ticket pool object for retrieving tickets
     */
    public CustomerRunner (int ticketsPerRetrieval, int releaseInterval, boolean priority, TicketPool ticketPool) {
        if (priority) { //Increasing id depending on customer type
            this.priorityCustomerId = priorityCustomerNo++;
        } else {
            this.customerId = customerNo++;
        }
        this.ticketsPerRetrieval = ticketsPerRetrieval;
        this.retrievalInterval = releaseInterval;
        this.priority = priority;
        this.ticketPool = ticketPool;
    }

    /**
     * The main logic for the customer thread. It continuously retrieves tickets from the ticket pool
     * until the thread is interrupted or paused.
     */
    @Override
    public void run() {
        try {
            while (true){
                while (!paused) { //Running if not paused
                    if (Thread.currentThread().isInterrupted())  {
                        if (priority) { //Logging if the thread is interrupted
                            logger.info("[Priority] Customer thread " + priorityCustomerId + " has been interrupted");
                        } else {
                            logger.info("Customer thread " + customerId + " has been interrupted");
                        }
                        return;
                    }
                    if (priority) { //Calling method depending on customer type
                        ticketPool.removeTicket(priorityCustomerId, ticketsPerRetrieval, priority);
                    } else {
                        ticketPool.removeTicket(customerId, ticketsPerRetrieval, priority);
                    }
                    Thread.sleep(retrievalInterval * 1000L);
                }
            }
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            if (priority) {
                logger.info("[Priority] Customer " + priorityCustomerId + " encountered error in thread.");
            } else {
                logger.info("Customer " + customerId + " encountered error in thread.");
            }
        }
    }

    /**
     * Pauses all customer threads.
     */
    public static void stop() {
        paused = true;
    }

    /**
     * Resumes all paused customer threads.
     */
    public static void resume(){
        paused = false;
    }

    /**
     * Resets the customer IDs to 1.
     */
    public static void resetCustomerIds(){
        customerNo = 1;
        priorityCustomerNo = 1;
    }

    /**
     * Decreases the customer ID by one.
     */
    public static void reduceId(){
        customerNo--;
    }

    /**
     * Decreases the priority customer ID by one.
     */
    public static void reducePriorityId(){
        priorityCustomerNo--;
    }
}
