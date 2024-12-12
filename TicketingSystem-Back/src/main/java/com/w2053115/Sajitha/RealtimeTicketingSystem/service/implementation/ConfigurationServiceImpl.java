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

/**
 * Implementation of the ConfigurationService interface.
 * Manages loading, saving, and initializing system configurations from a JSON file.
 */

@Service
@Data
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    SystemService systemService;

    @Autowired
    Configuration config;

    // ObjectMapper for mapping JSON data to Configuration object and vice versa
    private final ObjectMapper mapper = new ObjectMapper();
    // File path for the configuration JSON file
    private final String filePath = "configuration.json";

    // Logger for logging operations
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);

    /**
     * Loads the configuration from a JSON file and updates the Configuration object.
     * If the file is not found or mapping fails, appropriate logs are generated.
     *
     * @return The loaded Configuration object, or null if an error occurs.
     */
    @Override
    public Configuration loadConfiguration() {
        try {
            File file = new File(filePath);
            FileReader readFile = new FileReader(file); // Reading from the file
            // Mapping JSON file to Configuration object
            config.updateConfiguration(mapper.readValue(readFile, Configuration.class));
            systemService.initializer(); // Initialize TicketPool based on the loaded configuration
            logger.info("Configuration loaded from existing file");
            return config;
        } catch (FileNotFoundException e) {
            logger.error("Error reading the file");
            return null;
        } catch (IOException e) {
            logger.error("Error mapping the file");
            return null;
        }
    }

    /**
     * Saves the given Configuration object to a JSON file and reloads it into the system.
     *
     * @param configuration The Configuration object to save.
     * @return A message indicating success or an exception in case of failure.
     */
    @Override
    public String saveConfiguration(Configuration configuration) {
        try {
            File file = new File(filePath);
            FileWriter writeFile = new FileWriter(file);
            mapper.writeValue(writeFile, configuration); // Writing the Configuration object to JSON
            loadConfiguration(); // Reloading the saved configuration
            logger.info("Configuration file saved");
            return "Configuration file saved";
        } catch (Exception e) {
            logger.error("Error writing to the file");
            throw new RuntimeException();
        }
    }

    /**
     * Automatically loads the configuration from the JSON file during application startup.
     * Annotated with @PostConstruct to ensure it executes after dependency injection.
     */
    @PostConstruct
    public void loadConfigurationAtStartup() {
        loadConfiguration();
    }
}
