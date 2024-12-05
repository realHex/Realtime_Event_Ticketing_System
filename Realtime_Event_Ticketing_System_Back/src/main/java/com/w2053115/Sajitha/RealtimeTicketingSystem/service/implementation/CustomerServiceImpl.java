package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Customer;
import com.w2053115.Sajitha.RealtimeTicketingSystem.repositary.CustomerRepo;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.TicketPool;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.ConfigurationService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.CustomerService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.SystemService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.runnable.CustomerRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final ArrayList<Thread> customerThreadList = new ArrayList<>();
    private final ArrayList<CustomerRunner> customerObjectList = new ArrayList<>();

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    ConfigurationService configurationService;

    @Autowired
    private TicketPool ticketPool;

    @Autowired
    private SystemService systemService;

    @Override
    public String createCustomer() {
        //Load the configuration parameters into an object
        Configuration configuration = configurationService.loadConfiguration();

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
        if (systemService.getStart()) {
            customerThread.start();
        }
        for (Thread x : customerThreadList) {
            System.out.println(x.getName());
        }
        return "Customer " + customerObject.getCustomerId() + " created successfully";
    }

    @Override
    public String removeCustomer() {
        try {
            if (customerRepo != null && !customerObjectList.isEmpty() && !customerThreadList.isEmpty()) {
                int customerId = customerObjectList.getLast().getCustomerId();

                customerRepo.delete(customerRepo.findFirstByOrderByCreatedDesc());

                //Delete customer object and thread
                customerObjectList.removeLast();
                customerThreadList.removeLast();

                System.out.println("Customer " + customerId + " Removed");
                for (Thread x : customerThreadList) {
                    System.out.println(x.getName());
                }
                return "Customer " + customerId + " Removed";
            } else {
                System.out.println("Customer objects are null");
                return "Customer objects are null";
            }
        } catch (NullPointerException e) {
            System.out.println("No records for customer");
            throw e;
        }
    }

    @Override
    public void startVendors9() {
        for (Thread thread : customerThreadList) {
            thread.start();
        }
    }

    @Override
    public void stopVendors() {
        try {
            for (Thread thread : customerThreadList) {
                thread.wait();
            }
        } catch (InterruptedException e) {
            System.out.println("Error while stopping customer threads");
        }
    }
}
