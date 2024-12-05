package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.dto.request.ExampleConfigurationSaveRequestDTO;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.ConfigurationService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.ExampleConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController //Indicate to spring that this is a controller
@CrossOrigin(origins = "http://localhost:27017") //For security, ensures both the request and response comes from the same site
@RequestMapping("api/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @GetMapping(path = "/load-configuration")
    public Configuration loadConfiguration() {

        return configurationService.loadConfiguration();
    }

    @PostMapping(path = "/save-configuration")
    public String saveConfiguration(@RequestBody Configuration configuration) {

        configurationService.saveConfiguration(configuration);
        return "Configuration Saved";
    }
}
