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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);

    @Override
    public Configuration loadConfiguration() {
        try {
            File file = new File (filePath);
            FileReader readFile = new FileReader(file);
            config.updateConfiguration((mapper.readValue(readFile,Configuration.class)));
            systemService.initializer();
            logger.info("Configuration loaded from existing file");
            return config;
        }
        catch (FileNotFoundException e) {
            logger.error("Error reading the file");
            return null;
        }
        catch (IOException e){
            //logger = "Error in the mapper."
            logger.error("Error mapping the file");
            return null;
        }
    }

    @Override
    public String saveConfiguration(Configuration configuration) {
        if (SystemState.getState()==SystemState.RUNNING){
            logger.info("Unable to save configuration while application is running");
            return "Unable to save configuration while application is running";
        }
        else {
            try {
                File file = new File(filePath);
                FileWriter writeFile = new FileWriter(file);
                mapper.writeValue(writeFile,configuration);
                loadConfiguration();
                logger.info("Configuration file saved");
                return "Configuration file saved";
            }
            catch (Exception e){
                logger.error("Error writing to the file");
                throw new RuntimeException();
            }
        }
    }

    @PostConstruct
    public void loadConfigurationAtStartup() {
        loadConfiguration();
    }
}
