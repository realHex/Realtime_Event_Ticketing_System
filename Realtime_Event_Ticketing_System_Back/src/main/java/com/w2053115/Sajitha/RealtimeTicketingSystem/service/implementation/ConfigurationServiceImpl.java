package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.ConfigurationService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    @Getter
    private Configuration loadedConfig;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String filePath = "configuration.json";


    @Override
    public Configuration loadConfiguration() {
        try {
            File file = new File (filePath);
            FileReader readFile = new FileReader(file);
            return mapper.readValue(readFile,Configuration.class);
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

    @Override
    public void saveConfiguration(Configuration configuration) {
        try {
            File file = new File(filePath);
            FileWriter writeFile = new FileWriter(file);
            mapper.writeValue(writeFile,configuration);
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
