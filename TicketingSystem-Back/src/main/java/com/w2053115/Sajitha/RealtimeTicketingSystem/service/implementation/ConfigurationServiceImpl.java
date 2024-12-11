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

    //To map values to the json
    private final ObjectMapper mapper = new ObjectMapper();
    //Filepath and name of the json
    private final String filePath = "configuration.json";

    //To display logs
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);

    @Override
    public Configuration loadConfiguration() {
        try {
            File file = new File (filePath);
            FileReader readFile = new FileReader(file); //Reading from the file
            //Using the mapper to map the json files to the configuration object
            config.updateConfiguration((mapper.readValue(readFile,Configuration.class)));
            systemService.initializer(); //Initializing ticketpool
            logger.info("Configuration loaded from existing file");
            return config;
        }
        catch (FileNotFoundException e) {
            logger.error("Error reading the file");
            return null;
        }
        catch (IOException e){
            logger.error("Error mapping the file");
            return null;
        }
    }

    @Override
    public String saveConfiguration(Configuration configuration) {
        try {
            File file = new File(filePath);
            FileWriter writeFile = new FileWriter(file);
            mapper.writeValue(writeFile,configuration); //Writing the object
            loadConfiguration(); //Loading the saved configuration
            logger.info("Configuration file saved");
            return "Configuration file saved";
        }
        catch (Exception e){
            logger.error("Error writing to the file");
            throw new RuntimeException();
        }
    }

    //To load values from file at startup
    @PostConstruct
    public void loadConfigurationAtStartup() {
        loadConfiguration();
    }
}
