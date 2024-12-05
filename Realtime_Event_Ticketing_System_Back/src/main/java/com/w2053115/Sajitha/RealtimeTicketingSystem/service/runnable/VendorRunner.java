package com.w2053115.Sajitha.RealtimeTicketingSystem.service.runnable;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.TicketPool;
import lombok.Data;

@Data
public class VendorRunner implements Runnable{
    private final int vendorId;
    private final int ticketsPerRelease;
    private final int releaseInterval;
    static int vendorNo = 1;
    private final TicketPool ticketPool;

    public VendorRunner(int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        this.vendorId = vendorNo++;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ticketPool.addTicket(vendorId, ticketsPerRelease);
                Thread.sleep(releaseInterval * 1000L);
            }
        }
        catch (InterruptedException e){
            //Log.log("WARNING", "Vendor " + vendorId + " encountered error in thread.");
        }
    }
}
