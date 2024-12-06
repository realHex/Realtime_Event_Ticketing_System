package com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    public String createCustomer();
    public String removeCustomer();
    public void startCustomers();
    public void stopCustomers();
    public void resetCustomers();
    public int getCustomers();
}
