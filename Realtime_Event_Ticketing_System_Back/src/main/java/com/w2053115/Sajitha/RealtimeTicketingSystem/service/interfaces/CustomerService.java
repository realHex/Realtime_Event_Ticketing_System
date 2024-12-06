package com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    String createCustomer();
    String removeCustomer();
    void startCustomers();
    void stopCustomers();
    void resumeCustomers();
    void resetCustomers();
    int getCustomers();
}
