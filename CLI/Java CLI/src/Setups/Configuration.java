package Setups;

import java.util.*;
public class Configuration {

    private static int totalTickets;
    private static int ticketReleaseRate;
    private static int customerRetrievalRate;
    private static int maxTicketCapacity;

    public static void initiateParameters() {
        totalTickets = getInput("Enter total tickets currently in the system : ");
        ticketReleaseRate = getInput("Enter ticket release rate (per second) : ");
        customerRetrievalRate = getInput("Enter customer retrieval rate (per second) : ");
        maxTicketCapacity = getInput("Enter max ticket capacity : ");

        while (maxTicketCapacity<totalTickets) {
            System.out.println("Max ticket capacity should be higher than total tickets");
            maxTicketCapacity = getInput("Enter max ticket capacity ");
        }
    }

    public static int getInput(String message){
        int value;
        while (true) {
            System.out.print(message);
            try {
                Scanner input = new Scanner(System.in);
                value = input.nextInt();
                if (value <= 0) {
                    System.out.println("Enter a valid number (cannot be less than 0)");
                } else {
                    break;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Enter a valid number");
            }
        }
        return value;
    }

    public static int getTotalTickets() {
        return totalTickets;
    }

    public static int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public static int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public static int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public static void setMaxTicketCapacity(int maxTicketCapacity) {
        Configuration.maxTicketCapacity = maxTicketCapacity;
    }
}


