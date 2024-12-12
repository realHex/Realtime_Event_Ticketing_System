package com.w2053115.Sajitha.RealtimeTicketingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a ticket in the ticketing system.
 * Contains details about the ticket such as ticket ID, name, type, and price.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private int ticketId;
    private String ticketName;
    private String type;
    private long price;
}
