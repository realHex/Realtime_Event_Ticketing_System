package org.w2053115.Setups.SharedTicketPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w2053115.Setups.Ticket;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A thread-safe ticket pool implementation that manages ticket distribution
 * between vendors and customers using concurrent access controls.
 *
 * This class provides synchronized methods for adding and removing tickets
 * with capacity management and thread coordination using ReentrantLock and Conditions.
 */
public class TicketPool implements TicketPoolOperations{
    private final List<Ticket> ticketPool  = Collections.synchronizedList(new ArrayList<>());
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition ticketsAdded = lock.newCondition();
    private final Condition ticketsRemoved = lock.newCondition();
    private int totalTickets;
    private final int maxTicketCapacity;

    private static final Logger logger = LogManager.getLogger(TicketPool.class);

    /**
     * Constructs a TicketPool with a specified number of initial tickets and maximum capacity.
     *
     * @param totalTickets Initial number of tickets to populate the pool
     * @param maxTicketCapacity Maximum number of tickets that can be in the pool at any time
     */
    public TicketPool(int totalTickets, int maxTicketCapacity){
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
        ticketPoolPopulator();
    }

    /**
     * Populates the ticket pool with an initial set of tickets.
     *
     * Creates default movie tickets for the initial ticket count specified during
     * the TicketPool construction. Each ticket is created with a unique ID,
     * fixed movie name, and standard price.
     */
    @Override
    public void ticketPoolPopulator() {
        for (int i = 1; i <= this.totalTickets; i++) {
            Ticket ticket = new Ticket(i, "Movie Ticket","Arcane", 2000);
            ticketPool.add(ticket);
        }
    }

    /**
     * Adds tickets to the pool from a vendor, with thread-safe capacity management.
     *
     * This method ensures that the total number of tickets does not exceed
     * the maximum ticket capacity. If the capacity would be exceeded, the
     * vendor thread waits until some tickets are removed.
     *
     * @param vendorId Identifier for the vendor adding tickets
     * @param noOfTickets Number of tickets the vendor wants to add
     */
    @Override
    public void addTicket(int vendorId, int noOfTickets) {
        lock.lock();
        try {
            //Checking if the number of tickets exceeds ticket capacity
            while(totalTickets + noOfTickets > maxTicketCapacity) {
                logger.info("Vendor {} is waiting for tickets to be purchased." +
                        " Total Tickets : {}", vendorId, totalTickets);
                ticketsRemoved.await(); //Pausing the thread until there's space
            }
            //Adding tickets to the list
            for (int i = 1; i<=noOfTickets; i++) {
                Ticket ticket = new Ticket(1,"Movie Ticket","Arcane", 2000);
                ticketPool.add(ticket);
            }
            totalTickets += noOfTickets;
            logger.info("Vendor {} added {} tickets. Total Tickets : {}",
                    vendorId, noOfTickets, totalTickets);

            ticketsAdded.signalAll(); //Notifying customer threads
        }
        catch (InterruptedException e) {
            logger.error("Vendor {} encountered error while adding ticket", vendorId);
            Thread.currentThread().interrupt();

        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Removes tickets from the pool for a customer, with thread-safe availability management.
     *
     * This method ensures that enough tickets are available for purchase. If not,
     * the customer thread waits until more tickets are added to the pool.
     *
     * @param customerId Identifier for the customer purchasing tickets
     * @param noOfTickets Number of tickets the customer wants to purchase
     */
    @Override
    public void removeTicket(int customerId, int noOfTickets) {
        lock.lock();
        try {
            //Checking if there are enough tickets to purchase
            while (totalTickets - noOfTickets < 0) {
                logger.info("Customer {} is waiting for tickets to be added." +
                        " Total Tickets : {}", customerId, totalTickets);
                ticketsAdded.await(); //Pausing the thread until there's more tickets
            }
            //Removing tickets from the list
            ticketPool.subList(0,noOfTickets).clear();

            totalTickets -= noOfTickets;
            logger.info("Customer {} purchased {} tickets. Total Tickets : {}",
                    customerId, noOfTickets, totalTickets);
            ticketsRemoved.signalAll(); //Notifying vendor threads
        }
        catch (InterruptedException e) {
            logger.error("Customer {} encountered error while purchasing ticket", customerId);
            Thread.currentThread().interrupt();
        }
        finally {
            lock.unlock();
        }
    }

}