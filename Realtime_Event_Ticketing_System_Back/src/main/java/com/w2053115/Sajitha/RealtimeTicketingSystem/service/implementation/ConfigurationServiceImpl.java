package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.ConfigurationService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    @Getter
    private Configuration loadedConfig;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String filePath = "src/main/java/com/w2053115/Sajitha/RealtimeTicketingSystem/configuration/configuration.json";


    @Override
    public Configuration loadConfiguration() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                //logger. File doesn't exist
                System.out.println("File doesn't exist");
            }
            return mapper.readValue(file,Configuration.class);
        }
        catch (Exception e) {
            //logger = "Error reading configuration file."
            System.out.println("Runtime exception in load config catch block");
            throw new RuntimeException();
        }
    }

    @Override
    public void saveConfiguration(Configuration configuration) {
        try {
            File file = new File(filePath);
            mapper.writeValue(file,configuration);
        }
        catch (Exception e){
            //logger = "Error writing to the configuration file."
            throw new RuntimeException();
        }
    }

    public void keepConfiguration(Configuration configuration){
        this.loadedConfig = loadConfiguration();
    }

}
