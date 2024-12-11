package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.SystemService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.SystemState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/system")
public class SystemController {

    @Autowired
    SystemService systemService;

    @PostMapping("/start")
    public void startApplication() {
        systemService.startApplication();
    }

    @PostMapping("/stop")
    public void stopApplication() {
        systemService.stopApplication();
    }

    @PostMapping("/reset")
    public void resetApplication() {
        systemService.resetApplication();
    }

    @GetMapping("/state")
    public SystemState getState() {
        return SystemState.getState();
    }
}
