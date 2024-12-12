package com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces;

import org.springframework.stereotype.Service;

/**
 * Interface defining the core system operations for the ticketing system.
 * It includes methods for initializing, starting, stopping, and resetting the application.
 */
@Service
public interface SystemService {

    /**
     * Initializes the application, setting up all necessary components.
     */
    void initializer();

    /**
     * Starts the application, enabling the core functionality of the system.
     *
     * @return a status message indicating the result of the start operation
     */
    String startApplication();

    /**
     * Stops the application, disabling core functionality and pausing operations.
     *
     * @return a status message indicating the result of the stop operation
     */
    String stopApplication();

    /**
     * Resets the application to its initial state, clearing all data and configurations.
     *
     * @return a status message indicating the result of the reset operation
     */
    String resetApplication();
}
