package com.w2053115.Sajitha.RealtimeTicketingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Represents a transaction log entry in the system.
 * This log records actions performed by actors (vendors or customers) along with the number of tickets involved.
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionLogs {
    @Id
    private String logId; //Automatically generated an objectId by MongoDB

    private String actor;
    private String action;
    private int noOfTickets;

    private LocalDateTime time;

    /**
     * Constructor to create a TransactionLog entry with an actor, action, and number of tickets.
     *
     * @param actor        The entity performing the action (e.g., vendor, customer).
     * @param action       The action being performed (e.g., added tickets, purchased tickets).
     * @param noOfTickets  The number of tickets involved in the action.
     */
    public TransactionLogs(String actor, String action, int noOfTickets) {
        this.actor = actor;
        this.action = action;
        this.noOfTickets = noOfTickets;
        this.time = LocalDateTime.now();
    }
}
