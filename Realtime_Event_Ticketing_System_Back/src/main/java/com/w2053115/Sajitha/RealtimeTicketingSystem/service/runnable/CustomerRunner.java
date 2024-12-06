package com.w2053115.Sajitha.RealtimeTicketingSystem.service.runnable;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.TicketPool;
import lombok.Data;

@Data
public class CustomerRunner implements Runnable{
    private int customerId;
    private int ticketsPerRetrieval;
    private int retrievalInterval;
    private int priority;
    static int customerNo = 1;
    private final TicketPool ticketPool;
    private static volatile boolean paused = false;

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
            while (true){
                while (!paused) {
                    if (Thread.currentThread().isInterrupted())  {
                        System.out.println("Customer thread " + customerId + " has been interrupted");
                        return;
                    }
                    ticketPool.removeTicket(customerId, ticketsPerRetrieval);
                    Thread.sleep(retrievalInterval * 1000L);
                }
            }
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            //Log.log("WARNING", "Vendor " + customerId + " encountered error in thread.");
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
    }

    public static void reduceId(){
        customerNo--;
    }
}
