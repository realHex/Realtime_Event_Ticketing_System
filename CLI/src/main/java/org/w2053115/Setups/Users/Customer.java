package org.w2053115.Setups.Users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w2053115.Setups.AbstractTicketHandler;
import org.w2053115.Setups.SharedTicketPool.TicketPool;

public class Customer extends AbstractTicketHandler implements Runnable{
    private final int customerId;
    private final int ticketsPerRetrieval;
    private final int retrievalInterval;
    private final int priority;
    static int customerNo = 1;
    static boolean running = false;

    private static final Logger logger = LogManager.getLogger(Customer.class);

    public Customer (TicketPool ticketPool, int ticketsPerRelease, int releaseInterval, int priority) {
        super(ticketPool);
        this.customerId = customerNo++;
        this.ticketsPerRetrieval = ticketsPerRelease;
        this.retrievalInterval = releaseInterval;
        this.priority = priority;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ticketPool.removeTicket(customerId, ticketsPerRetrieval);
                Thread.sleep(retrievalInterval * 1000L); //waiting after removing a ticket
            }
        }
        catch (InterruptedException e){
            if (running) {
                logger.error("Customer {} has been interrupted", customerId);
            } else {
                logger.info("Customer {} has been stopped", customerId);
            }
            Thread.currentThread().interrupt();
        }
    }

    public static void setRunningTrue(){
        running = true;
    }
    public static void setRunningFalse(){
        running = false;
    }

    public static void resetCustomerNo(){
        customerNo = 1;
    }
}
