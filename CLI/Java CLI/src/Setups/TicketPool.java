package Setups;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private static final List<Ticket> ticketPool  = Collections.synchronizedList(new ArrayList<>());
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition ticketsAdded = lock.newCondition();
    private static final Condition ticketsRemoved = lock.newCondition();
    private static int totalTickets = Configuration.getTotalTickets();
    private static final int maxTicketCapacity = Configuration.getMaxTicketCapacity();

    //Populating the list with integers
    public static void makingInitialTicketPool () {
        for (int i = 1; i<=totalTickets; i++) {
            Ticket ticket = new Ticket(1,"Movie Ticket", 2000);
            ticketPool.add(ticket);
        }
    }

    public static void addTicket (int vendorId, int noOfTickets) {
        lock.lock();
        try {
            //Checking if the number of tickets exceeds ticket capacity
            while(totalTickets + noOfTickets > maxTicketCapacity) {
                Log.log("INFO",System.currentTimeMillis() + " " + "Vendor " + vendorId + " is waiting for tickets to be purchased. Total tickets : " + totalTickets);
                ticketsRemoved.await(); //Pausing the thread until there's space
            }
            //Adding tickets to the list
            for (int i = 1; i<=noOfTickets; i++) {
                Ticket ticket = new Ticket(1,"Movie Ticket", 2000);
                ticketPool.add(ticket);
            }
            totalTickets += noOfTickets;
            Log.log("INFO", System.currentTimeMillis() + " " + "Vendor " + vendorId + " added " + noOfTickets + " tickets. Total tickets : " + totalTickets);
            ticketsAdded.signalAll(); //Notifying customer threads
        }
        catch (InterruptedException e) {
            Log.log("WARNING", "Vendor " + vendorId + " encountered error while adding ticket");
        }
        finally {
            lock.unlock();
        }
    }
    public static void removeTicket (int customerId, int noOfTickets) {
        lock.lock();
        try {
            //Checking if there are enough tickets to purchase
            while (totalTickets - noOfTickets < 0) {
                Log.log("INFO", System.currentTimeMillis() + " " + "Customer " + customerId + " is waiting for tickets to be added. Total tickets : " + totalTickets);
                ticketsAdded.await(); //Pausing the thread until there's more tickets
            }
            //Removing tickets from the list
            ticketPool.subList(0,noOfTickets).clear();

            totalTickets -= noOfTickets;
            Log.log("INFO", System.currentTimeMillis() + " " + "Customer " + customerId + " purchased " + noOfTickets + " tickets. Total tickets : " + totalTickets);
            ticketsRemoved.signalAll(); //Notifying vendor threads
        }
        catch (InterruptedException e) {
            Log.log("WARNING", "Customer " + customerId + " encountered error while purchasing ticket");
        }
        finally {
            lock.unlock();
        }
    }
}
