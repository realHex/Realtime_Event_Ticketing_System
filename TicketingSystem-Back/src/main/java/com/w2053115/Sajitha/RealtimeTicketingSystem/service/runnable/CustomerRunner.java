package com.w2053115.Sajitha.RealtimeTicketingSystem.service.runnable;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.TicketPool;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class CustomerRunner implements Runnable{
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

    public CustomerRunner (int ticketsPerRetrieval, int releaseInterval, boolean priority, TicketPool ticketPool) {
        if (priority) {
            this.priorityCustomerId = priorityCustomerNo++;
        } else {
            this.customerId = customerNo++;
        }
        this.ticketsPerRetrieval = ticketsPerRetrieval;
        this.retrievalInterval = releaseInterval;
        this.priority = priority;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        try {
            while (true){
                while (!paused) {
                    if (Thread.currentThread().isInterrupted())  {
                        if (priority) {
                            logger.info("[Priority] Customer thread " + priorityCustomerId + " has been interrupted");
                        } else {
                            logger.info("Customer thread " + customerId + " has been interrupted");
                        }
                        return;
                    }
                    if (priority) {
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

    public static void stop() {
        paused = true;
    }
    public static void resume(){
        paused = false;
    }

    public static void resetCustomerIds(){
        customerNo = 1;
        priorityCustomerNo = 1;
    }

    public static void reduceId(){
        customerNo--;
    }

    public static void reducePriorityId(){
        priorityCustomerNo--;
    }
}
