package org.w2053115.Setups.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


public class Configuration {

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private static final Logger logger = LogManager.getLogger(Configuration.class);

    public void initiateParameters() {
        this.totalTickets = getInput("Enter total tickets currently in the system : ");
        this.ticketReleaseRate = getInput("Enter ticket release rate (per second) : ");
        this.customerRetrievalRate = getInput("Enter customer retrieval rate (per second) : ");
        this.maxTicketCapacity = getInput("Enter max ticket capacity : ");

        while (maxTicketCapacity<totalTickets) {
            System.out.println("Max ticket capacity should be higher than total tickets");
            maxTicketCapacity = getInput("Enter max ticket capacity ");
        }
    }

    public static int getInput(String message){
        int value;
        //Re-prompting until the user enters a valid number
        while (true) {
            System.out.print(message);
            try {
                Scanner input = new Scanner(System.in);
                value = input.nextInt();
                if (value <= 0) {

                    logger.info("Enter a valid number (cannot be less than 0)");
                } else {
                    break;
                }
            }
            catch (InputMismatchException e) {
                logger.info("Enter a valid number");
            }
        }
        return value;
    }

    public int getTotalTickets() {
        return this.totalTickets;
    }

    public int getTicketReleaseRate() {
        return this.ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return this.customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return this.maxTicketCapacity;
    }

    public String toString(){
        return "[Total tickets : " + this.totalTickets +
                ", Ticket Release Rate : " + this.ticketReleaseRate +
                ", Customer Purchase Rate : " + this.customerRetrievalRate +
                ", Max Ticket Capacity : " + this.maxTicketCapacity + "]";
    }
}


