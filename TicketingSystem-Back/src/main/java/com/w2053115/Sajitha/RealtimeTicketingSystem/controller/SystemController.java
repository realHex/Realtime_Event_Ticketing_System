package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.SystemService;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.SystemState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling HTTP requests related to the overall system state.
 * It provides endpoints for starting, stopping, resetting the application, and retrieving the current system state.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from the Angular frontend
@RequestMapping("api/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * Endpoint to start the application. It triggers the start of the ticketing system.
     */
    @PostMapping("/start")
    public void startApplication() {
        systemService.startApplication();
    }

    /**
     * Endpoint to stop the application. It triggers the stop of the ticketing system.
     */
    @PostMapping("/stop")
    public void stopApplication() {
        systemService.stopApplication();
    }

    /**
     * Endpoint to reset the application. It triggers the reset of the ticketing system, clearing all current states.
     */
    @PostMapping("/reset")
    public void resetApplication() {
        systemService.resetApplication();
    }

    /**
     * Endpoint to retrieve the current state of the system.
     *
     * @return The current state of the system (RUNNING, PAUSED, or STOPPED).
     */
    @GetMapping("/state")
    public SystemState getState() {
        return SystemState.getState();
    }
}
