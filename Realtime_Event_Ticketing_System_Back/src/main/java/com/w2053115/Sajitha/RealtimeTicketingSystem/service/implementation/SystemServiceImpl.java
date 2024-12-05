package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.ConfigurationService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemServiceImpl implements SystemService {

    Configuration loadedConfig;

    boolean start;

    @Autowired
    ConfigurationService configurationService;

    @Override
    public void initializer() {
        try {
            loadedConfig = configurationService.loadConfiguration();
        }
        catch(Exception e) {
            //Log = "Error while reading the file"
            throw new RuntimeException();
        }
    }

    @Override
    public String startApplication() {
        return null;
    }

    @Override
    public String stopApplication() {
        return null;
    }

    @Override
    public String resetApplication() {
        return null;
    }

    public boolean getStart(){
        return start;
    }
}
