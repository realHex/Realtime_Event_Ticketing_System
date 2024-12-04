package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.dto.request.ConfigurationSaveRequestDTO;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController //Indicate to spring that this is a controller
@CrossOrigin //For security, ensures both the request and response comes from the same site
@RequestMapping("api/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @PostMapping(path = "/save-configuration")
    public String saveConfiguration(@RequestBody ConfigurationSaveRequestDTO saveRequestDTO) {

        return configurationService.saveConfiguration(saveRequestDTO);

    }
}
