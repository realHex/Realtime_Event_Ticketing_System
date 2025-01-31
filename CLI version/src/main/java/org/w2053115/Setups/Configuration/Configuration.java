package org.w2053115.Setups.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * This class holds the configuration parameters for the ticket simulation program.
 * It provides methods to initialize these parameters from user input and retrieve their values.
 */
public class Configuration {

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private static final Logger logger = LogManager.getLogger(Configuration.class);

    /**
     * Prompts the user for configuration parameters and initializes them in the object.
     * - Total tickets currently in the system
     * - Ticket release rate (tickets per second)
     * - Customer retrieval rate (tickets per second)
     * - Max ticket capacity
     * Ensures maxTicketCapacity is greater than totalTickets before continuing.
     */
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

    /**
     * Prompts the user for a valid integer input and returns the value.
     * - Re-prompts until a valid integer is entered (greater than 0).
     * - Logs informative messages for invalid input using the logger.
     *
     * @param message The message to display to the user when prompting for input
     * @return The valid integer entered by the user
     */
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

    /**
     * @return The total number of tickets currently in the system
     */
    public int getTotalTickets() {
        return this.totalTickets;
    }

    /**
     * @return The ticket release rate (tickets per second)
     */
    public int getTicketReleaseRate() {
        return this.ticketReleaseRate;
    }

    /**
     * @return The customer retrieval rate (tickets per second)
     */
    public int getCustomerRetrievalRate() {
        return this.customerRetrievalRate;
    }

    /**
     * @return The maximum ticket capacity for the system
     */
    public int getMaxTicketCapacity() {
        return this.maxTicketCapacity;
    }

    /**
     * @return A string representation of the configuration object with all parameter values
     */
    public String toString(){
        return "[Total tickets : " + this.totalTickets +
                ", Ticket Release Rate : " + this.ticketReleaseRate +
                ", Customer Purchase Rate : " + this.customerRetrievalRate +
                ", Max Ticket Capacity : " + this.maxTicketCapacity + "]";
    }
}