package com.w2053115.Sajitha.RealtimeTicketingSystem.service;

import org.springframework.stereotype.Service;

@Service
public interface SystemService {

    public void initializer();

    String startApplication();

    String stopApplication();

    String resetApplication();
}
