package com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    void createCustomer(boolean priority);

    void removeCustomer(boolean priority);

    void startCustomers();
    void stopCustomers();
    void resumeCustomers();
    void resetCustomers();
    int getCustomers();
    int getPriorityCustomers();
}
