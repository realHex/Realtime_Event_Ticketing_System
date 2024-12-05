package com.w2053115.Sajitha.RealtimeTicketingSystem.service;


import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Ticket;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Data
public class TicketPool {
    private final List<Ticket> ticketPool = Collections.synchronizedList(new ArrayList<>());
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition ticketsAdded = lock.newCondition();
    private final Condition ticketsRemoved = lock.newCondition();
    private int totalTickets;
    private int maxTicketCapacity;

    private int totalAddedTickets;
    private int totalSoldTickets;


    public void ticketpoolInitializer(int totalTickets, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;

        for (int i = 1; i<=totalTickets; i++) {
            Ticket ticket = new Ticket(1,"G17","Arcane", 2000);
            ticketPool.add(ticket);
        }

        totalAddedTickets = 0;
        totalSoldTickets = 0;

    }

    public void addTicket (int vendorId, int noOfTickets) {
        lock.lock();
        try {
            //Checking if the number of tickets exceeds ticket capacity
            while(totalTickets + noOfTickets > maxTicketCapacity) {
                //Log.log("INFO",System.currentTimeMillis() + " " + "Vendor " + vendorId + " is waiting for tickets to be purchased. Total tickets : " + totalTickets);
                ticketsRemoved.await(); //Pausing the thread until there's space
            }
            //Adding tickets to the list
            for (int i = 1; i<=noOfTickets; i++) {
                Ticket ticket = new Ticket(1,"G17","Arcane", 2000);
                ticketPool.add(ticket);
            }
            totalTickets += noOfTickets;
            totalAddedTickets += 1;
            //Log.log("INFO", System.currentTimeMillis() + " " + "Vendor " + vendorId + " added " + noOfTickets + " tickets. Total tickets : " + totalTickets);
            ticketsAdded.signalAll(); //Notifying customer threads
        }
        catch (InterruptedException e) {
            //Log.log("WARNING", "Vendor " + vendorId + " encountered error while adding ticket");
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
                //Log.log("INFO", System.currentTimeMillis() + " " + "Customer " + customerId + " is waiting for tickets to be added. Total tickets : " + totalTickets);
                ticketsAdded.await(); //Pausing the thread until there's more tickets
            }
            //Removing tickets from the list
            ticketPool.subList(0,noOfTickets).clear();

            totalTickets -= noOfTickets;
            totalSoldTickets += 1;
            //Log.log("INFO", System.currentTimeMillis() + " " + "Customer " + customerId + " purchased " + noOfTickets + " tickets. Total tickets : " + totalTickets);
            ticketsRemoved.signalAll(); //Notifying vendor threads
        }
        catch (InterruptedException e) {
            //Log.log("WARNING", "Customer " + customerId + " encountered error while purchasing ticket");
        }
        finally {
            lock.unlock();
        }
    }
}

