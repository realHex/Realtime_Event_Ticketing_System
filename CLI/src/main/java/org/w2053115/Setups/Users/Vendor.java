package org.w2053115.Setups.Users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w2053115.Setups.AbstractTicketHandler;
import org.w2053115.Setups.SharedTicketPool.TicketPool;

/**
 * This class represents a vendor who interacts with a shared ticket pool.
 *
 * Vendors are responsible for adding tickets to the pool at a predefined
 * interval and quantity. They inherit from the `AbstractTicketHandler` class
 * to provide a common base for handling ticket operations.
 */
public class Vendor extends AbstractTicketHandler implements Runnable {

    private final int vendorId;
    private final int ticketsPerRelease;
    private final int releaseInterval;

    static int vendorNo = 1;

    static boolean running = false;
    private static final Logger logger = LogManager.getLogger(Vendor.class);

    /**
     * Constructs a new Vendor object.
     *
     * @param ticketPool The shared ticket pool used by the vendor.
     * @param ticketsPerRelease The number of tickets added per interval.
     * @param releaseInterval The interval (in seconds) between ticket additions.
     */
    public Vendor(TicketPool ticketPool, int ticketsPerRelease, int releaseInterval) {
        super(ticketPool);
        this.vendorId = vendorNo++;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
    }

    /**
     * Implements the `run()` method from the `Runnable` interface.
     *
     * This method defines the vendor's behavior in a separate thread. It continuously
     * attempts to add tickets to the pool at the specified interval until interrupted.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ticketPool.addTicket(vendorId, ticketsPerRelease);
                Thread.sleep(releaseInterval * 1000L); // Waiting after vendors add a ticket
            }
        } catch (InterruptedException e) {
            if (running) {
                logger.error("Vendor {} has been interrupted", vendorId);
            } else {
                logger.info("Vendor {} has been stopped", vendorId);
            }
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Sets the `running` flag to true, indicating that vendor threads are running.
     */
    public static void setRunningTrue() {
        running = true;
    }

    /**
     * Sets the `running` flag to false, indicating that vendor threads are stopped.
     */
    public static void setRunningFalse() {
        running = false;
    }

    /**
     * Resets the `vendorNo` counter to 1 for assigning unique IDs.
     */
    public static void resetVendorNo() {
        vendorNo = 1;
    }
}