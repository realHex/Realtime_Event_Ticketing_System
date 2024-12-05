package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.dto.request.ExampleConfigurationSaveRequestDTO;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.ExampleConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController //Indicate to spring that this is a controller
@CrossOrigin //For security, ensures both the request and response comes from the same site
@RequestMapping("api/test-configuration")
public class ExampleConfigurationController {

    @Autowired
    private ExampleConfigurationService exampleConfigurationService;

    @PostMapping(path = "/test-save-configuration")
    public String saveConfiguration(@RequestBody ExampleConfigurationSaveRequestDTO saveRequestDTO) {

        return exampleConfigurationService.saveConfiguration(saveRequestDTO);

    }
}
