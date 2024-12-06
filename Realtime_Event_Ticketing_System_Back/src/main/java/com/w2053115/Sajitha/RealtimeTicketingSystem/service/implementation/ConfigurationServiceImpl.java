package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.SystemState;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.ConfigurationService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.SystemService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@Data
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    SystemService systemService;

    @Autowired
    Configuration config;

    private final ObjectMapper mapper = new ObjectMapper();
    private final String filePath = "configuration.json";

    @Override
    public Configuration loadConfiguration() {
        if (SystemState.getState()==SystemState.RUNNING){
            System.out.println("Unable to load configuration while application is running");
            return null;
        }
        else {
            try {
                File file = new File (filePath);
                FileReader readFile = new FileReader(file);
                config.updateConfiguration((mapper.readValue(readFile,Configuration.class)));
                systemService.initializer();
                return config;
            }
            catch (FileNotFoundException e) {
                //logger = "Error reading configuration file."
                System.out.println("Error reading the file");
                throw new RuntimeException();
            }
            catch (IOException e){
                //logger = "Error in the mapper."
                System.out.println("Error mapping the file");
                throw new RuntimeException();
            }
        }
    }

    @Override
    public void saveConfiguration(Configuration configuration) {
        if (SystemState.getState()==SystemState.RUNNING){
            System.out.println("Unable to save configuration while application is running");
        }
        else {
            try {
                File file = new File(filePath);
                FileWriter writeFile = new FileWriter(file);
                mapper.writeValue(writeFile,configuration);
                loadConfiguration();
            }
            catch (Exception e){
                //logger = "Error writing to the configuration file."
                throw new RuntimeException();
            }
        }
    }

    @PostConstruct
    @Override
    public void loadConfigurationAtStartup() {
        loadConfiguration();
    }
}
