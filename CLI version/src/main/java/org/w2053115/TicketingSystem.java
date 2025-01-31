package org.w2053115;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w2053115.Setups.Configuration.Configuration;
import org.w2053115.Setups.Configuration.ConfigurationUtility;
import org.w2053115.Setups.SharedTicketPool.TicketPool;
import org.w2053115.Setups.Users.Customer;
import org.w2053115.Setups.Users.Vendor;

/**
 * A realtime ticketing system simulation that manages vendors and customers
 * interacting with a shared ticket pool. This application allows dynamic
 * configuration, creation of vendors and customers, and simulation management.
 *
 * @author Sajitha Fonseka
 */
public class TicketingSystem {

    private static boolean running = false; //Indicating if the system is running
    private static final ArrayList<Vendor> vendorObjectList = new ArrayList<Vendor>();
    private static final ArrayList<Thread> vendorThreadList = new ArrayList<Thread>();
    private static final ArrayList<Customer> customerObjectList = new ArrayList<Customer>();
    private static final ArrayList<Thread> customerThreadList = new ArrayList<Thread>();
    private static final Logger logger = LogManager.getLogger(TicketingSystem.class);

    /**
     * Main method to run the Realtime Ticketing System Simulation.
     * Handles configuration, ticket pool initialization,
     * and user interaction for starting and resetting the simulation.
     */
    public static void main(String[] args) {

        int noOfVendors;
        int noOfCustomers;

        Configuration parameters = new Configuration();  //Making a configuration object

        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println();
            System.out.println("\tRealtime Ticketing System Simulation");
            System.out.println("\t -  Developed by Sajitha Fonseka  -");
            System.out.println();
            System.out.print("Do you want try loading configuration? (y for yes, n for no) : ");

            //Setting parameters
            parameters = setConfiguration();

            //Initializing ticketpool
            TicketPool ticketPool = new TicketPool(parameters.getTotalTickets(), parameters.getMaxTicketCapacity());
            ticketPool.ticketPoolPopulator(); //Add the already existing total tickets

            //Setting up vendors and creating threads
            noOfVendors = Configuration.getInput("Enter number of vendors for simulation : ");
            createVendors(noOfVendors, ticketPool, parameters.getTicketReleaseRate());

            //Setting up customers and creating threads
            noOfCustomers = Configuration.getInput("Enter number of customers for simulation : ");
            createCustomers(noOfCustomers, ticketPool, parameters.getCustomerRetrievalRate(), 1);

            //Starting the application
            System.out.print("Press 's' to start, Press 'r' to reset : ");
            startApplication();
        }
    }

    /**
     * Sets up the configuration parameters for the ticketing system.
     * Allows loading a previous configuration or creating a new one.
     *
     * @return Configuration object with system parameters
     */
    public static Configuration setConfiguration(){
        Configuration parameters = new Configuration();  //Making a configuration object
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String option = scanner.nextLine().toLowerCase();

            if (option.equals("y")) {
                //Checking if there's a json file to load from and loading from it
                if (ConfigurationUtility.loadConfiguration()!=null) {
                    parameters = ConfigurationUtility.loadConfiguration();
                    logger.info("Previous configuration loaded. {}", parameters.toString());
                    return parameters;

                    //Asking user for parameters if there's no json file to load from
                } else {
                    System.out.println("No configuration to load from");
                    parameters.initiateParameters();
                    ConfigurationUtility.saveConfiguration(parameters);
                    logger.info("New configuration saved. {}", parameters.toString());
                    return parameters;
                }
                //Asking user for parameters if the user doesn't want to load from json file
            } else if (option.equals("n")) {
                parameters.initiateParameters();
                ConfigurationUtility.saveConfiguration(parameters);
                logger.info("New configuration saved. {}", parameters.toString());
                return parameters;
            } else {
                System.out.print("Invalid input. Enter 'y' to load / 'n' to create new: ");
            }
        }
    }

    /**
     * Creates vendor objects and corresponding threads for the simulation.
     *
     * @param noOfVendors Number of vendors to create
     * @param ticketPool Shared ticket pool for vendors to interact with
     * @param ticketsPerRelease Number of tickets each vendor can release per iteration
     */
    public static void createVendors(int noOfVendors, TicketPool ticketPool, int ticketsPerRelease) {
        //Creating vendor objects for the amount of vendors
        for (int i = 0; i < noOfVendors; i++) {
            Vendor vendor = new Vendor(ticketPool, ticketsPerRelease, 1);

            vendorObjectList.add(vendor);
            Thread vendorThread = new Thread(vendor); //Creating threads for each vendor
            vendorThreadList.add(vendorThread);
        }
        System.out.println("Vendors created");;
    }

    /**
     * Creates customer objects and corresponding threads for the simulation.
     *
     * @param noOfCustomers Number of customers to create
     * @param ticketPool Shared ticket pool for customers to interact with
     * @param ticketsPerRelease Number of tickets each customer can retrieve per iteration
     * @param priority Priority level for customer threads
     */
    public static void createCustomers(int noOfCustomers, TicketPool ticketPool, int ticketsPerRelease, int priority) {
        //Creating customer objects for the amount of customers
        for (int i = 0; i < noOfCustomers; i++) {
            Customer customer = new Customer(ticketPool, ticketsPerRelease, 1, priority);

            customerObjectList.add(customer);
            Thread customerThread = new Thread(customer); //Creating threads for each customer
            customerThreadList.add(customerThread);
        }
        System.out.println("Customers created");;
    }

    /**
     * Starts all threads for a given list of threads (either vendors or customers).
     *
     * @param userName Name of the thread type (for logging and display purposes)
     * @param listOfThreads List of threads to be started
     */
    public static void startThreads(String userName, ArrayList<Thread> listOfThreads) {
        //Starting each thread in the array
        for (Thread thread : listOfThreads) {
            thread.start();
        }
        System.out.printf(userName +" threads started");
        System.out.println();

    }

    /**
     * Handles the application start and reset logic.
     * Allows user to start the simulation or reset the system.
     */
    public static void startApplication(){
        Scanner scanner = new Scanner(System.in);

        while (true){

            String option = scanner.nextLine().toLowerCase();

            if (option.equals("s")) {
                logger.info("Application starting...");
                running = true;

                try {
                    Thread.sleep(1000);
                    System.out.print("Press 'enter' to stop while running");
                    for (int i = 0; i < 3; i++) { //Slowing for the user to read the stop button
                        Thread.sleep(1000);
                        System.out.print(" .");
                    }
                    System.out.println();
                    startThreads("Vendor", vendorThreadList);
                    Vendor.setRunningTrue();
                    startThreads("Customer", customerThreadList);
                    Customer.setRunningTrue();

                    //Waiting for the user to stop
                    scanner.nextLine();
                    stopApplication();

                    //Resetting application
                    try {
                        Thread.sleep(1000);
                        resetVendors();
                        resetCustomers();
                        logger.info("Application stopped");
                    } catch (InterruptedException e) {
                        logger.error("Error in main thread while waiting for the application to finish", e);
                    }
                    break;

                } catch (InterruptedException e) {
                    logger.error("Main thread interrupted while sleeping", e);
                }
                //Resetting vendors and customers
            } else if (option.equals("r")) {
                resetVendors();
                resetCustomers();
                logger.info("Application reset");
                break;
            } else {
                System.out.print("Invalid input. Enter 's' to start / 'r' to reset: ");
            }
        }
    }

    /**
     * Stops all running vendor and customer threads.
     */
    public static void stopApplication(){
        //Interrupting all running threads
        Vendor.setRunningFalse();
        for (Thread thread : vendorThreadList) {
            thread.interrupt();
        }

        Customer.setRunningFalse();
        for (Thread thread : customerThreadList) {
            thread.interrupt();
        }
    }

    /**
     * Resets all vendor-related objects and threads.
     * Clears vendor lists and resets vendor numbering.
     */
    public static void resetVendors(){
        vendorObjectList.clear();
        vendorThreadList.clear();

        Vendor.resetVendorNo();
        System.out.println("Vendors reset");
    }

    /**
     * Resets all customer-related objects and threads.
     * Clears customer lists and resets customer numbering.
     */
    public static void resetCustomers(){
        customerObjectList.clear();
        customerThreadList.clear();

        Customer.resetCustomerNo();
        System.out.println("Customers reset");
    }

}