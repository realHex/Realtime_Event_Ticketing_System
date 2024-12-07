package org.w2053115.Setups.Users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w2053115.Setups.AbstractTicketHandler;
import org.w2053115.Setups.SharedTicketPool.TicketPool;

public class Vendor extends AbstractTicketHandler implements Runnable{
    private final int vendorId;
    private final int ticketsPerRelease;
    private final int releaseInterval;
    static int vendorNo = 1;

    static boolean running = false;
    private static final Logger logger = LogManager.getLogger(Vendor.class);


    public Vendor (TicketPool ticketPool, int ticketsPerRelease, int releaseInterval) {
        super(ticketPool);
        this.vendorId = vendorNo++;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ticketPool.addTicket(vendorId, ticketsPerRelease);
                Thread.sleep(releaseInterval * 1000L); //Waiting after vendors add a ticket
            }
        }
        catch (InterruptedException e){
            if (running) {
                logger.error("Vendor {} has been interrupted", vendorId);
            } else {
                logger.info("Vendor {} has been stopped", vendorId);
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
    public static void resetVendorNo(){
        vendorNo = 1;
    }
}
