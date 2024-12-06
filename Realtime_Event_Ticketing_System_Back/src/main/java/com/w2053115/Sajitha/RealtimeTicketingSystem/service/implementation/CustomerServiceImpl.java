package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Customer;
import com.w2053115.Sajitha.RealtimeTicketingSystem.repository.CustomerRepo;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.SystemState;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.TicketPool;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.CustomerService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.runnable.CustomerRunner;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.runnable.VendorRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerServiceImpl implements CustomerService {

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
        if (SystemState.getState()==SystemState.RUNNING) {
            customerThread.start();
        }
        for (Thread x : customerThreadList) {
            System.out.println(x.getName());
        }
        noOfCustomers++;
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

                System.out.println("Customer " + customerId + " Removed");
                for (Thread x : customerThreadList) {
                    System.out.println(x.getName());
                }
                return "Customer " + customerId + " Removed";
            } else {
                System.out.println("Customer objects are null");
                noOfCustomers--;
                return "Customer objects are null";
            }
        } catch (NullPointerException e) {
            System.out.println("No records for customer");
            throw e;
        }
    }

    @Override
    public void startCustomers() {
        for (Thread thread : customerThreadList) {
            thread.start();
        }
        System.out.println("Customers started");
    }

    @Override
    public void stopCustomers() {
        for (CustomerRunner customerRunner : customerObjectList) {
            customerRunner.stop();
        }
        System.out.println("Customers stopped");
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
        noOfCustomers = 0;
        System.out.println("Customers reset");
    }

    @Override
    public int getCustomers() {
        return noOfCustomers;
    }
}
