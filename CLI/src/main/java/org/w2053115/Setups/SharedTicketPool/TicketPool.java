package org.w2053115.Setups.SharedTicketPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w2053115.Setups.Ticket;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool implements TicketPoolOperations{
    private final List<Ticket> ticketPool  = Collections.synchronizedList(new ArrayList<>());
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition ticketsAdded = lock.newCondition();
    private final Condition ticketsRemoved = lock.newCondition();
    private int totalTickets;
    private final int maxTicketCapacity;

    private static final Logger logger = LogManager.getLogger(TicketPool.class);

    public TicketPool(int totalTickets, int maxTicketCapacity){
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
        ticketPoolPopulator();
    }

    //Adding tickets to the ticketpool before the threads start
    @Override
    public void ticketPoolPopulator() {
        for (int i = 1; i <= this.totalTickets; i++) {
            Ticket ticket = new Ticket(i, "Movie Ticket","Arcane", 2000);
            ticketPool.add(ticket);
        }
    }

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
            Thread.currentThread().interrupt();
            logger.error("Vendor {} encountered error while adding ticket", vendorId);
        }
        finally {
            lock.unlock();
        }
    }

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
            Thread.currentThread().interrupt();
            logger.error("Customer {} encountered error while purchasing ticket", customerId);
        }
        finally {
            lock.unlock();
        }
    }

}
