package com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import org.springframework.stereotype.Service;

@Service
public interface ConfigurationService {

    Configuration loadConfiguration();
    void saveConfiguration(Configuration configuration);


}
