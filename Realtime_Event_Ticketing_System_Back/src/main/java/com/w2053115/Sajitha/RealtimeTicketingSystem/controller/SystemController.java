package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/system")
public class SystemController {

    @Autowired
    SystemService systemService;

    @PostMapping("/start")
    public String startApplication() {
        return systemService.startApplication();
    }

    @PostMapping("/stop")
    public String stopApplication() {
        return systemService.stopApplication();
    }

    @PostMapping("/reset")
    public String resetApplication() {
        return systemService.resetApplication();
    }
}
