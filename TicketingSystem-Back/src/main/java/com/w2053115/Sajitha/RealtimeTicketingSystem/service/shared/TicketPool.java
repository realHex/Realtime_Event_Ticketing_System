package com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared;


import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Ticket;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.TransactionLogsService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Manages the ticket pool in a real-time event ticketing system.
 * Provides functionality for adding and removing tickets while ensuring thread safety using locks and conditions.
 * It also logs transactions and ensures that the pool's size doesn't exceed the maximum ticket capacity.
 */
@Service
@Data
public class TicketPool {

    @Autowired
    TransactionLogsService transactionLogsService;

    private static final Logger logger = LoggerFactory.getLogger(TicketPool.class);

    private final List<Ticket> ticketPool = Collections.synchronizedList(new ArrayList<>());
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition ticketsAdded = lock.newCondition();
    private final Condition ticketsRemoved = lock.newCondition();
    private int totalTickets;
    private int maxTicketCapacity;

    private int totalAddedTickets = 0;
    private int totalPurchasedTickets = 0;

    /**
     * Initializes the ticket pool with the specified number of tickets and maximum capacity.
     * Populates the pool with the starting number of tickets.
     *
     * @param totalTickets the initial number of tickets in the pool
     * @param maxTicketCapacity the maximum allowed number of tickets in the pool
     */
    public void initializeTicketpool(int totalTickets, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;

        //Populates the ticketpool with the starting amount of total tickets
        for (int i = 1; i<=totalTickets; i++) {
            Ticket ticket = new Ticket(1,"G17","Arcane", 2000);
            ticketPool.add(ticket);
        }
    }

    /**
     * Resets the ticket pool, clearing all tickets and resetting ticket counts and capacities.
     */
    public void resetTicketpool(){
        ticketPool.clear();
        totalTickets = 0;
        maxTicketCapacity = 0;
        totalAddedTickets = 0;
        totalPurchasedTickets = 0;
    }

    /**
     * Adds the specified number of tickets to the pool, ensuring the total does not exceed the maximum capacity.
     * If the capacity is reached, the vendor thread waits until tickets are purchased.
     *
     * @param vendorId the unique identifier of the vendor adding tickets
     * @param noOfTickets the number of tickets to be added to the pool
     */
    public void addTicket(int vendorId, int noOfTickets) {
        lock.lock();
        try {
            //Checking if the number of tickets exceeds ticket capacity
            while (totalTickets + noOfTickets > maxTicketCapacity) {
                logger.info("Vendor {} is waiting for tickets to be purchased." +
                        " Total Tickets : {}", vendorId, totalTickets);

                transactionLogsService.addRealtime("Vendor " + vendorId,
                        "is waiting for tickets to be purchased", totalTickets);

                ticketsRemoved.await(); //Pausing the thread until there's space
            }
            //Adding tickets to the list
            for (int i = 1; i <= noOfTickets; i++) {
                Ticket ticket = new Ticket(1,"G17","Arcane", 2000);
                ticketPool.add(ticket);
            }
            totalTickets += noOfTickets;
            totalAddedTickets += noOfTickets;
            logger.info("Vendor {} added {} tickets. Total Tickets : {}",
                    vendorId, noOfTickets, totalTickets);

            transactionLogsService.addRealtime("Vendor " + vendorId,
                    "added " + noOfTickets + " tickets", totalTickets);

            transactionLogsService.addDatabase("Vendor " + vendorId,
                    "added", noOfTickets);

            ticketsAdded.signalAll(); //Notifying customer threads
        } catch (InterruptedException e) {
            logger.error("Vendor {} encountered error while adding ticket", vendorId);
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes the specified number of tickets from the pool when a customer purchases them.
     * If there are insufficient tickets, the customer thread waits until more tickets are added.
     *
     * @param customerId the unique identifier of the customer purchasing tickets
     * @param noOfTickets the number of tickets to be purchased
     * @param priority indicates whether the customer is a priority customer
     */
    public void removeTicket(int customerId, int noOfTickets, boolean priority) {
        lock.lock();
        try {
            //Checking if there are enough tickets to purchase
            while (totalTickets - noOfTickets < 0) {
                if (priority) {
                    logger.info("[Priority] Customer {} is waiting for tickets to be added." +
                            " Total Tickets : {}", customerId, totalTickets);

                    transactionLogsService.addRealtime("[Priority Customer ] " + customerId,
                            "is waiting for tickets to be added ", totalTickets);

                } else {
                    logger.info("Customer {} is waiting for tickets to be added." +
                            " Total Tickets : {}", customerId, totalTickets);

                    transactionLogsService.addRealtime("Customer " + customerId,
                            "is waiting for tickets to be added ", totalTickets);
                }
                ticketsAdded.await(); //Pausing the thread until there's more tickets
            }
            //Removing tickets from the list
            ticketPool.subList(0, noOfTickets).clear();

            totalTickets -= noOfTickets;
            totalPurchasedTickets += noOfTickets;
            if (priority) {
                logger.info("[Priority] Customer {} purchased {} tickets. Total Tickets : {}",
                        customerId, noOfTickets, totalTickets);

                transactionLogsService.addRealtime("[Priority] Customer " + customerId,
                        "purchased " + noOfTickets + " tickets", totalTickets);

                transactionLogsService.addDatabase("[Priority] Customer" + customerId,
                        "purchased", noOfTickets);
            } else {
                logger.info("Customer {} purchased {} tickets. Total Tickets : {}",
                        customerId, noOfTickets, totalTickets);

                transactionLogsService.addRealtime("Customer " + customerId,
                        "purchased " + noOfTickets + " tickets", totalTickets);

                transactionLogsService.addDatabase("Customer" + customerId,
                        "purchased", noOfTickets);
            }

            ticketsRemoved.signalAll(); //Notifying vendor threads
        } catch (InterruptedException e) {
            if (priority) {
                logger.error("[Priority] Customer {} encountered error while purchasing ticket", customerId);
            } else {
                logger.error("Customer {} encountered error while purchasing ticket", customerId);
            }

            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}


