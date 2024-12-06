package com.w2053115.Sajitha.RealtimeTicketingSystem.service.runnable;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.TicketPool;
import lombok.Data;

@Data
public class VendorRunner implements Runnable{
    private final int vendorId;
    private final int ticketsPerRelease;
    private final int releaseInterval;
    static int vendorNo = 1;
    private final TicketPool ticketPool;
    private static volatile boolean paused = false;

    public VendorRunner(int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        this.vendorId = vendorNo++;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        try {
            while (true){
                while (!paused) {
                    if (Thread.currentThread().isInterrupted())  {
                        System.out.println("Vendor thread " + vendorId + " has been interrupted");
                        return;
                    }
                    ticketPool.addTicket(vendorId, ticketsPerRelease);
                    Thread.sleep(releaseInterval * 1000L);
                }
            }
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            //Log.log("WARNING", "Vendor " + vendorId + " encountered error in thread.");
        }
    }

    public static void stop() {
        paused = true;
    }

    public static void resume(){
        paused = false;
    }

    public static void resetVendorIds(){
        vendorNo = 1;
    }

    public static void reduceId(){
        vendorNo--;
    }
}
