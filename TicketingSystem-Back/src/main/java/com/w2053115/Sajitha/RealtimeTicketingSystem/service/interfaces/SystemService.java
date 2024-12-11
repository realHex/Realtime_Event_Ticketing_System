package com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface SystemService {

    void initializer();

    String startApplication();

    String stopApplication();

    String resetApplication();

}
