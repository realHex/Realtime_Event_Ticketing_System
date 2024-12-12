package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling HTTP requests related to the configuration of the ticketing system.
 * It provides endpoints to load and save the system configuration.
 */
@RestController // Indicate to spring that this is a controller
@CrossOrigin(origins = "http://localhost:4200") // For security, ensures both the request and response comes from the same site
@RequestMapping("api/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    /**
     * Endpoint to load the current system configuration.
     *
     * @return The current configuration of the system.
     */
    @GetMapping(path = "/load-configuration")
    public Configuration loadConfiguration() {
        return configurationService.loadConfiguration();
    }

    /**
     * Endpoint to save a new system configuration.
     *
     * @param configuration The new configuration to be saved.
     */
    @PostMapping(path = "/save-configuration")
    public void saveConfiguration(@RequestBody Configuration configuration) {
        configurationService.saveConfiguration(configuration);
    }
}
