package com.w2053115.Sajitha.RealtimeTicketingSystem.service.runnable;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.TicketPool;
import lombok.Data;

@Data
public class CustomerRunner implements Runnable{
    private int customerId;
    private int ticketsPerRetrieval;
    private int retrievalInterval;
    private int priority;
    static int customerNo = 1;
    private final TicketPool ticketPool;

    public CustomerRunner (int ticketsPerRetrieval, int releaseInterval, int priority, TicketPool ticketPool) {
        this.customerId = customerNo++;
        this.ticketsPerRetrieval = ticketsPerRetrieval;
        this.retrievalInterval = releaseInterval;
        this.priority = priority;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ticketPool.removeTicket(customerId, ticketsPerRetrieval);
                Thread.sleep(retrievalInterval * 1000L);
            }
        }
        catch (InterruptedException e){
            //Log.log("WARNING", "Vendor " + customerId + " encountered error in thread.");
        }
    }
}
