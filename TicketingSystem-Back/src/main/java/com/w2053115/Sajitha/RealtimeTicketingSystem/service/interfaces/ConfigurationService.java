package com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import org.springframework.stereotype.Service;

/**
 * Interface defining the methods for handling configuration data in the system.
 * It provides functionality to load and save configuration settings.
 */
@Service
public interface ConfigurationService {

    /**
     * Loads the current configuration settings.
     *
     * @return the current configuration
     */
    Configuration loadConfiguration();

    /**
     * Saves the provided configuration settings.
     *
     * @param configuration the configuration to be saved
     * @return a status message indicating the result of the save operation
     */
    String saveConfiguration(Configuration configuration);
}
