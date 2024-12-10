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

    private final ArrayList<Thread> customerThreadList = new ArrayList<>();
    private final ArrayList<CustomerRunner> customerObjectList = new ArrayList<>();
    private int noOfCustomers = 0;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    Configuration configuration;

    @Autowired
    private TicketPool ticketPool;

    @Override
    public String createCustomer() {
        if (configuration.getMaxTicketCapacity()  == 0 || configuration.getTotalTickets()  == 0 ||
                configuration.getTicketReleaseRate()  == 0 || configuration.getCustomerRetrievalRate()  == 0) {
            logger.warn("Load a Configuration before adding customers");
            return "Load a Configuration before adding customers";
        }

        //Add customer details to the database
        Customer customer = new Customer(
                configuration.getCustomerRetrievalRate(),
                1,
                "Gwen Stacy",
                "0729950215",
                1
        );
        customerRepo.save(customer);


        //Create customer object (for making the thread)
        CustomerRunner customerObject = new CustomerRunner(
                configuration.getCustomerRetrievalRate(),
                1,
                1,
                ticketPool
        );
        customerObjectList.add(customerObject);

        //Creating customer threads and storing it for accessing
        Thread customerThread = new Thread(customerObject);
        customerThreadList.add(customerThread);
        if (SystemState.getState()==SystemState.RUNNING || SystemState.getState()==SystemState.PAUSED) {
            customerThread.start();
        }
        for (Thread x : customerThreadList) {
            System.out.println(x.getName());
        }
        noOfCustomers++;
        logger.info("Customer " + customerObject.getCustomerId() + " created successfully");
        return "Customer " + customerObject.getCustomerId() + " created successfully";
    }

    @Override
    public String removeCustomer() {
        try {
            if (customerRepo != null && !customerObjectList.isEmpty() && !customerThreadList.isEmpty()) {
                int customerId = customerObjectList.getLast().getCustomerId();

                customerRepo.delete(customerRepo.findFirstByOrderByCreatedDesc());

                //Delete customer object and thread
                customerThreadList.getLast().interrupt();
                customerObjectList.removeLast();
                customerThreadList.removeLast();

                noOfCustomers--;
                CustomerRunner.reduceId();
                logger.info("Customer " + customerId + " Removed");
                return "Customer " + customerId + " Removed";
            } else {
                logger.warn("There's no customers added");
                return "There's no customers added";
            }
        } catch (NullPointerException e) {
            logger.error("No records for customer");
            throw e;
        }
    }

    @Override
    public void startCustomers() {
        for (Thread thread : customerThreadList) {
            thread.start();
        }
        logger.info("Customers started");
    }

    @Override
    public void stopCustomers() {
        for (CustomerRunner customerRunner : customerObjectList) {
            CustomerRunner.stop();
        }
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
            //Collections.fill(customerObjectList, null);
            customerObjectList.clear();
            customerThreadList.clear();
        }
        CustomerRunner.resume();
        CustomerRunner.resetCustomerIds();
        noOfCustomers = 0;
        logger.info("Customers reset");
    }

    @Override
    public int getCustomers(){
        return noOfCustomers;
    }
}
