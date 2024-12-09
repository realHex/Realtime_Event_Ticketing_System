package com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared;


import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Ticket;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Data
public class TicketPool {
    private static final Logger logger = LoggerFactory.getLogger(TicketPool.class);

    private final List<Ticket> ticketPool = Collections.synchronizedList(new ArrayList<>());
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition ticketsAdded = lock.newCondition();
    private final Condition ticketsRemoved = lock.newCondition();
    private int totalTickets;
    private int maxTicketCapacity;

    private int totalAddedTickets;
    private int totalSoldTickets;


    public void initializeTicketpool(int totalTickets, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;

        for (int i = 1; i<=totalTickets; i++) {
            Ticket ticket = new Ticket(1,"G17","Arcane", 2000);
            ticketPool.add(ticket);
        }

        totalAddedTickets = 0;
        totalSoldTickets = 0;

    }
    public void resetTicketpool(){
        ticketPool.clear();
        totalTickets = 0;
        maxTicketCapacity = 0;
        totalAddedTickets = 0;
        totalSoldTickets = 0;
    }

    public void addTicket (int vendorId, int noOfTickets) {
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
                Ticket ticket = new Ticket(1,"G17","Arcane", 2000);
                ticketPool.add(ticket);
            }
            totalTickets += noOfTickets;
            totalAddedTickets += 1;
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
    public void removeTicket (int customerId, int noOfTickets) {
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
            totalSoldTickets += 1;
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

