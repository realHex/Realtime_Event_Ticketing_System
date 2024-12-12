package com.w2053115.Sajitha.RealtimeTicketingSystem.model;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * Represents the configuration settings for the ticketing system.
 * Holds values for the total number of tickets, ticket release rate, customer retrieval rate, and the maximum ticket capacity.
 * Provides a method to update the configuration with new values.
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Configuration {

    private int totalTickets = 0;
    private int ticketReleaseRate = 0;
    private int customerRetrievalRate = 0;
    private int maxTicketCapacity = 0;

    /**
     * Updates the current configuration with values from another Configuration object.
     *
     * @param configuration the Configuration object to copy values from
     */
    public void updateConfiguration(Configuration configuration){
        this.totalTickets = configuration.getTotalTickets();
        this.ticketReleaseRate = configuration.getTicketReleaseRate();
        this.customerRetrievalRate = configuration.getCustomerRetrievalRate();
        this.maxTicketCapacity = configuration.getMaxTicketCapacity();
    }
}
