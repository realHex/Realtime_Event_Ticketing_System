package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Customer;
import com.w2053115.Sajitha.RealtimeTicketingSystem.repository.CustomerRepo;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.SystemState;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.TicketPool;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.CustomerService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.runnable.CustomerRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    //Normal Customer
    private final ArrayList<Thread> customerThreadList = new ArrayList<>();
    private final ArrayList<CustomerRunner> customerObjectList = new ArrayList<>();

    //Priority Customer
    private final ArrayList<Thread> priorityCustomerThreadList = new ArrayList<>();
    private final ArrayList<CustomerRunner> priorityCustomerObjectList = new ArrayList<>();

    private int noOfCustomers = 0;
    private int noOfPriorityCustomers = 0;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    Configuration configuration;

    @Autowired
    private TicketPool ticketPool;

    @Override
    public void createCustomer(boolean priority) {
        if (!isConfigurationLoaded()) {
            logger.warn("Load a Configuration before adding customers");
            return;
        }

        //Add customer details to the database
        Customer customer = new Customer(
                configuration.getCustomerRetrievalRate(),
                1,
                "Gwen Stacy",
                "0729950215",
                priority
        );
        customerRepo.save(customer);


        //Create customer object (for making the thread)
        CustomerRunner customerObject = new CustomerRunner(
                configuration.getCustomerRetrievalRate(),
                1,
                priority,
                ticketPool
        );

        //Adding to list depending on priority
        if (priority) {
            priorityCustomerObjectList.add(customerObject);
        } else {
            customerObjectList.add(customerObject);
        }

        //Creating customer threads and storing it for accessing
        Thread customerThread = new Thread(customerObject);

        //Setting thread priority to 10 if priority customer
        if (priority) {
            customerThread.setPriority(Thread.MAX_PRIORITY);
            priorityCustomerThreadList.add(customerThread);
        } else { //Normally it's 5
            customerThreadList.add(customerThread);
        }

        if (SystemState.getState()==SystemState.RUNNING || SystemState.getState()==SystemState.PAUSED) {
            customerThread.start();
        }

        if (priority) {
            noOfPriorityCustomers++;
            logger.info("[Priority] Customer {} created successfully", customerObject.getPriorityCustomerId());
        } else {
            noOfCustomers++;
            logger.info("Customer {} created successfully", customerObject.getCustomerId());
        }
    }

    @Override
    public void removeCustomer(boolean priority) {
        if (priority &&  customerRepo != null && !priorityCustomerObjectList.isEmpty() &&
                !priorityCustomerThreadList.isEmpty()) {
            try {

                int priorityCustomerId = priorityCustomerObjectList.getLast().getPriorityCustomerId();

                customerRepo.delete(customerRepo.findFirstByPriorityTrueOrderByCreatedDesc());

                //Delete priority customer object and thread
                priorityCustomerThreadList.getLast().interrupt();
                priorityCustomerObjectList.removeLast();
                priorityCustomerThreadList.removeLast();

                noOfPriorityCustomers--;
                CustomerRunner.reducePriorityId();
                logger.info("[Priority] Customer {} Removed", priorityCustomerId);
            } catch (NullPointerException e) {
                logger.error("No records for priority customer", e);
            }
        }

        if (!priority && customerRepo != null && !customerObjectList.isEmpty() && !customerThreadList.isEmpty()) {
            try {

                int customerId = customerObjectList.getLast().getCustomerId();

                customerRepo.delete(customerRepo.findFirstByPriorityFalseOrderByCreatedDesc());

                //Delete customer object and thread
                customerThreadList.getLast().interrupt();
                customerObjectList.removeLast();
                customerThreadList.removeLast();

                noOfCustomers--;
                CustomerRunner.reduceId();
                logger.info("Customer {} Removed", customerId);

            } catch (NullPointerException e) {
                logger.error("No records for customer", e);
            }
        }
    }

    @Override
    public void startCustomers() {
        for (Thread thread : priorityCustomerThreadList) {
            thread.start();
        }
        for (Thread thread : customerThreadList) {
            thread.start();
        }
        logger.info("Customers started");
    }

    @Override
    public void stopCustomers() {
        CustomerRunner.stop();
        logger.info("Customers stopped");
    }

    @Override
    public void resumeCustomers(){
        CustomerRunner.resume();
    }

    @Override
    public void resetCustomers() {
        if (!customerObjectList.isEmpty() && !customerThreadList.isEmpty()) {
            for (Thread thread : customerThreadList) {
                thread.interrupt();
            }

            customerObjectList.clear();
            customerThreadList.clear();
        }
        if (!priorityCustomerObjectList.isEmpty() && !priorityCustomerThreadList.isEmpty()) {
            for (Thread thread : priorityCustomerThreadList) {
                thread.interrupt();
            }

            priorityCustomerObjectList.clear();
            priorityCustomerThreadList.clear();
        }
        CustomerRunner.resume();
        CustomerRunner.resetCustomerIds();
        noOfCustomers = 0;
        noOfPriorityCustomers = 0;
        logger.info("Customers reset");
    }

    @Override
    public int getCustomers(){
        return noOfCustomers;
    }

    @Override
    public int getPriorityCustomers(){
        return noOfPriorityCustomers;
    }

    private boolean isConfigurationLoaded() {
        return (configuration.getMaxTicketCapacity() > 0 &&
                configuration.getTotalTickets() > 0 &&
                configuration.getTicketReleaseRate() > 0 &&
                configuration.getCustomerRetrievalRate() > 0);
    }
}
