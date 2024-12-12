package com.w2053115.Sajitha.RealtimeTicketingSystem.service.runnable;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.TicketPool;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a vendor interacting with the ticket pool in a real-time event ticketing system.
 * Implements the Runnable interface to allow for vendor threads that continuously release tickets
 * to the ticket pool at specified intervals.
 */
@Data
public class VendorRunner implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(VendorRunner.class);
    private final int vendorId;
    private final int ticketsPerRelease;
    private final int releaseInterval;
    static int vendorNo = 1;
    private final TicketPool ticketPool;
    private static volatile boolean paused = false;

    /**
     * Constructor to initialize a vendor with ticket release details and assign them a unique ID.
     *
     * @param ticketsPerRelease the number of tickets the vendor releases per interval
     * @param releaseInterval the interval time (in seconds) between each ticket release
     * @param ticketPool the shared ticket pool object for releasing tickets
     */
    public VendorRunner(int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        this.vendorId = vendorNo++;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    /**
     * The main logic for the vendor thread. It continuously releases tickets into the ticket pool
     * until the thread is interrupted or paused.
     */
    @Override
    public void run() {
        try {
            while (true){
                while (!paused) { //Running if not paused
                    if (Thread.currentThread().isInterrupted())  { //Logging if the thread is interrupted
                        logger.info("Vendor thread " + vendorId + " has been interrupted");
                        return;
                    }
                    ticketPool.addTicket(vendorId, ticketsPerRelease);
                    Thread.sleep(releaseInterval * 1000L);
                }
            }
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            logger.error("Vendor " + vendorId + " encountered error in thread.");
        }
    }

    /**
     * Pauses all vendor threads.
     */
    public static void stop() {
        paused = true;
    }

    /**
     * Resumes all paused vendor threads.
     */
    public static void resume(){
        paused = false;
    }

    /**
     * Resets the vendor IDs to 1.
     */
    public static void resetVendorIds(){
        vendorNo = 1;
    }

    /**
     * Decreases the vendor ID by one.
     */
    public static void reduceId(){
        vendorNo--;
    }
}
